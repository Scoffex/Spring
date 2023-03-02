package com.progetto.service_impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.kafka.common.Uuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progetto.conversion.ConversionAnagraficAccount;
import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.dto.BankAccountClientDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.ClientBankRepo;
import com.progetto.service.IclientBankAccountService;
import com.progetto.utils.Constants;
import com.progetto.utils.Crypthography;
import com.progetto.utils.DecryptorData;
import com.progetto.utils.IbanGenerator;

@Service
public class ClientBankAccountServiceImpl implements IclientBankAccountService {

	private final Logger logger = LoggerFactory.getLogger(ClientBankAccountServiceImpl.class);

	@Autowired
	private ClientAccountBankRepo repo;

	@Autowired
	private ClientBankRepo repoClient;

	
	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi save(String panCode) throws GenericError {
		List<BankAccount> accounts;
		ClientBank client;
		ResponseFromApi response;
		try {
			client = repoClient.findByPanCode(Crypthography.encrypt(panCode));
			createBankAccount(client);
			accounts = repo.findByClientId(client.getId());
			response = ConversionAnagraficAccount.fromDomainToResponse(client, accounts, Constants.SAVE_DONE,
					HttpStatus.OK);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(
					Constants.ERROR_CLIENT_BANK_ACCOUNT_SAVE).append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}

		return DecryptorData.decryptIban(response);

	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public BankAccount createBankAccount(ClientBank client) throws GenericError {
		BankAccount ba = new BankAccount();
		try {
			ba.setId(new StringBuilder(Uuid.randomUuid().toString()).append(String.valueOf(new Date().getTime())).toString());
			ba.setAmount(0);
			ba.setCurrency(Constants.CURRENCY);
			ba.setIban(Crypthography.encrypt(IbanGenerator.generateIban()));
			ba.setClientBank(client);
			ba.setDateCreationAccount(LocalDate.now());
			logger.info(ba.toString());
			repo.save(ba);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(Constants.ERROR_CLIENT_GENERATE_BANK_ACCOUNT).append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return ba;
	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi findAll() throws GenericError {
		ResponseFromApi response = new ResponseFromApi();
		try {
			response.setBankAccount(ConversionAnagraficAccount.fromListDomainBanotoListDtoBank(repo.findAll()));
			response.setMessage(Constants.GET_ALL_DONE);
			response.setStatudCode(HttpStatus.OK);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(Constants.ERROR_CLIENT_FIND_ALL_ACCOUNT).append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return response;
	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi delete(String iban) throws GenericError {
		try {
			BankAccount ba = repo.findByIban(Crypthography.encrypt(iban));
			repo.delete(ba);

		} catch (Exception e) {
			StringBuilder sb = new StringBuilder(Constants.ERROR_CLIENT_BANK_ACCOUNT_DELETE).append(iban)
					.append(Constants.ENTITY_NOT_FOUND);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return new ResponseFromApi(new StringBuilder("ENTITY WITH IBAN: ").append(iban).append(" DELETED").toString(),
				HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi findByIban(String iban) throws GenericError {
		ResponseFromApi response = new ResponseFromApi();

		try {
			BankAccount ba = repo.findByIban(Crypthography.encrypt(iban));
			response.setBankAccountSingle(ConversionAnagraficAccount.fromDomainBankToDto(ba));

			response.setMessage(Constants.ACCOUNT_FOUND);
			response.setStatudCode(HttpStatus.OK);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR FINDING ENTITY, IBAN ").append(iban)
					.append(" NOT PRESENT IN THE SYSTEM - ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return DecryptorData.decryptIban(response);
	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi update(BankAccountClientDto dto, String iban) throws GenericError {
		ResponseFromApi response;
		try {
			BankAccount ba = repo.findByIban(Crypthography.encrypt(iban));
			BankAccount baUpdated = ConversionAnagraficAccount.fromDtoBankToDomain(dto);
			baUpdated.setClientBank(ba.getClientBank());
			baUpdated.setId(ba.getId());
			repo.save(baUpdated);
			response = new ResponseFromApi(Constants.SAVE_DONE, HttpStatus.OK);
			response.setBankAccountSingle(dto);
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR UPDATING ENTITY BANKACCOUNT. ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return response;

	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi findByPanCode(String panCode) throws GenericError {
		ResponseFromApi response = new ResponseFromApi();

		try {
			ClientBank cb = repoClient.findByPanCode(Crypthography.encrypt(panCode));
			List<BankAccount> listOfBankAccount = repo.findAll();
			List<BankAccount> listOfBankAccountFiltred = new ArrayList<>();
			for(BankAccount ba : listOfBankAccount) {
				if(ba.getClientBank().getId().equals(cb.getId()))
					listOfBankAccountFiltred.add(ba);
			}
			//list.forEach(x -> list2.addAll( x.getClientBank().getId().equals(cb.getId())));
			//list = list.stream()
					//repo.findAll().stream().filter(x -> x.getClientBank().getId().equals(cb.getId()))
				//	.toList(); 
			response.setBankAccount(ConversionAnagraficAccount.fromListDomainBanotoListDtoBank(listOfBankAccountFiltred));
			response.setMessage(Constants.ACCOUNT_FOUND);
			response.setStatudCode(HttpStatus.OK);

		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR FINDING ENTITY, PANCODE ").append(panCode)
					.append(" NOT PRESENT IN THE SYSTEM - ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return DecryptorData.decryptIban(response);
	}

}
