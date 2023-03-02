package com.progetto.service_impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.kafka.common.Uuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.progetto.thread.ThreadVerification;
import com.progetto.utils.Constants;
import com.progetto.utils.Crypthography;
import com.progetto.utils.GenericMethod;
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

	@Autowired
	private PasswordEncoder encoder;
	@Override
	@Transactional(rollbackOn = GenericError.class)
	public ResponseFromApi save(ClientBankDto userDTO, HttpServletRequest httpRequest) throws GenericError {
		ClientBank clientBank;
		List<BankAccount> bankAccounts;
		try {
			userDTO.setPassword(encoder.encode(userDTO.getPassword()));
			clientBank = ConversionAnagraficAccount.fromDtoToDomain(userDTO);
			bankAccounts = new ArrayList<BankAccount>();
			String id = new StringBuilder(Uuid.randomUuid().toString()).append(String.valueOf(new Date().getTime()))
					.toString();
			clientBank.setId(id);
			clientBank.setCreationDate(LocalDate.now());
			clientBank.setExpirationDate(LocalDate.now().plusYears(5));
			clientBank.setStatus(Constants.IS_VERIFICATING);
			clientBank.setPanCode(Crypthography.encrypt(generatorPanCode.panCodeGenerator()));
			ClientBank client = clientRepo.save(clientBank);
			publisher.publishEvent(new RegistrationCompleteEvent(client, GenericMethod.generateUrl(httpRequest)));
			ThreadVerification thread = new ThreadVerification(client.getPanCode(), clientRepo);
			thread.start();
			bankAccounts.add(serviceBankCount.createBankAccount(client));
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR IN SAVE METHOD FOR BANK CLIENT: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return ConversionAnagraficAccount.fromDomainToResponse(clientBank, bankAccounts, Constants.SAVE_DONE,
				HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackOn = GenericError.class)
	public List<ResponseFromApi> findAll() throws GenericError {
		List<ResponseFromApi> responses = new ArrayList<>();
		try {
			List<ClientBank> users = clientRepo.findAll();
			if (!users.isEmpty()) {
				for (ClientBank user : users) {
					List<BankAccount> bankAccount = clientAccountRepo.findByClientId(user.getId());
					responses.add(ConversionAnagraficAccount.fromDomainToResponse(user, bankAccount, null, null));
				}
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

//	public String findIdByEmail(String email) {
//		String id = "";
//		try {
//			ClientBank user = clientRepo.findByEmail(email);
//			id = user.getId();
//		} catch (Exception e) {
//			logger.error("EMAIL NOT FOUND", e);
//		}
//		return id;
//	}

	@Override
	@Transactional(rollbackOn = GenericError.class)
	public ResponseFromApi findByEmail(String email) throws GenericError {
		ClientBank user;
		List<BankAccount> ba;
		logger.info(email);
		try {
			user = clientRepo.findByEmail(email);
			ba = clientAccountRepo.findByClientId(user.getId());
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(Constants.CLIENT_NOT_FOUND).append(" ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		logger.info("NON HA DATO ERRORE");
		return ConversionAnagraficAccount.fromDomainToResponse(user, ba, Constants.CLIENT_FOUND, HttpStatus.OK);
	}

}
