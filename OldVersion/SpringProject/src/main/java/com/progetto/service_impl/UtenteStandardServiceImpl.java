package com.progetto.service_impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.progetto.conversion.ConversionAnagraficAccount;
import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.dto.ClientBankDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.event.RegistrationCompleteEvent;
import com.progetto.exception.GenericError;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.ClientBankRepo;
import com.progetto.service.IutenteStandardService;
import com.progetto.utils.Constants;
import com.progetto.utils.Crypthography;
import com.progetto.utils.DecryptorData;
import com.progetto.utils.PanCodeGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class UtenteStandardServiceImpl implements IutenteStandardService {

	private final Logger logger = LoggerFactory.getLogger(UtenteStandardServiceImpl.class);

	@Autowired
	private ClientBankRepo clientRepo;

	@Autowired
	private ClientAccountBankRepo clientAccountRepo;

	@Autowired
	private PanCodeGenerator generatorPanCode;

	@Autowired
	private ClientBankAccountServiceImpl serviceBankCount;


	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	
	@Override
	@Transactional(rollbackOn = GenericError.class)
	public ResponseFromApi save(ClientBankDto userDTO, String panCode, HttpServletRequest request) throws GenericError {
		userDTO.setPassword(Crypthography.encrypt(userDTO.getPassword()));
		ClientBank userStd = ConversionAnagraficAccount.fromDtoToDomain(userDTO);
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		try {
			if (panCode.isEmpty()) {
				panCode = generatorPanCode.panCodeGenerator();
				userStd.setCreationDate(LocalDate.now());
				userStd.setExpirationDate(LocalDate.now().plusYears(5));
				userStd.setPanCode(Crypthography.encrypt(panCode));
				ClientBank client = clientRepo.save(userStd);
				publisher.publishEvent(new RegistrationCompleteEvent(client, generateUrl(request)));
				bankAccounts.add(serviceBankCount.createBankAccount(client));
			} else {
				logger.info(panCode);
				logger.info(Crypthography.encrypt(panCode));
				ClientBank client = clientRepo.findByPanCode(Crypthography.encrypt(panCode));
				client.setBorningDate(userDTO.getBirth());
				client.setFirstName(userDTO.getName());
				client.setEmail(userDTO.getEmail());
				client.setPassword(userDTO.getPassword());
				client.setLastName(userDTO.getSurname());
			}
			
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR IN SAVE METHOD FOR BANK CLIENT: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return DecryptorData.decryptPanCodeIban(ConversionAnagraficAccount.fromDomainToResponse(userStd, bankAccounts, Constants.SAVE_DONE, HttpStatus.OK));
		
	}

	
	
	
	private String generateUrl(HttpServletRequest request) {
		return new StringBuilder("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath()).toString();
	}




	@Override
	@Transactional(rollbackOn = GenericError.class)
	public List<ResponseFromApi> findAll() throws GenericError {
		List<ResponseFromApi> responses = new ArrayList<>();
		try {
			List<ClientBank> users = clientRepo.findAll();
			for (ClientBank user : users) {
				List<BankAccount> bankAccount = clientAccountRepo.findByClientId(user.getId());
				responses.add(ConversionAnagraficAccount.fromDomainToResponse(user, bankAccount, null, null));
			}
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR IN FIND ALL: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return responses;
	}

	
	
	
	@Override
	@Transactional(rollbackOn = GenericError.class)
	public ResponseFromApi delete(String panCode) throws GenericError {
		try {
			ClientBank client = clientRepo.findByPanCode(Crypthography.encrypt(panCode));
			clientRepo.delete(client);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR DELETING CLIENT WITH PAN-CODE ").append(panCode)
					.append(" ENTITY NOT PRESENT IN THE SYSTEM. ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return new ResponseFromApi(Constants.DELETE_DONE, HttpStatus.OK);
	}

	
	
	
	@Override
	@Transactional(rollbackOn = GenericError.class)
	public ResponseFromApi update(ClientBankDto userDTO, String panCode) throws GenericError {
		ClientBank client = clientRepo.findByPanCode(Crypthography.encrypt(panCode));
		client.setBorningDate(userDTO.getBirth());
		client.setFirstName(userDTO.getName());
		client.setEmail(userDTO.getEmail());
		client.setPassword(Crypthography.encrypt(userDTO.getPassword()));
		client.setLastName(userDTO.getSurname());
		return ConversionAnagraficAccount.fromDomainToResponse(client, null, "CLIENT CORRECTLY UPDATED", HttpStatus.OK);
	}

	public int findIdByEmail(String email) {
		int id = 0;
		try {
			ClientBank user = clientRepo.findByEmail(email);
			id = user.getId();
		} catch (Exception e) {
			logger.error("EMAIL NOT FOUND", e);
		}
		return id;
	}
	
	public static void main(String[] args) {
		System.out.println(Crypthography.decrypt("d5CfSpVWhwa/aRLCLXyQvQ=="));
	}

	
	
	
	@Override
	@Transactional(rollbackOn = GenericError.class)
	public ResponseFromApi findByEmail(String email) throws GenericError {
		ClientBank user;
		List<BankAccount> ba;
		try {
			user = clientRepo.findByEmail(email);
			ba = clientAccountRepo.findByClientId(user.getId());
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(Constants.CLIENT_NOT_FOUND).append(" ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return ConversionAnagraficAccount.fromDomainToResponse(user, ba, Constants.CLIENT_FOUND, HttpStatus.OK);
	}
	
	
	

}
