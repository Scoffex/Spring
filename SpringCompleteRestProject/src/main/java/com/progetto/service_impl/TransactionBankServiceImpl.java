package com.progetto.service_impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.kafka.common.Uuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progetto.conversion.ConversionTransactionOperation;
import com.progetto.domain.BankAccount;
import com.progetto.domain.Transaction;
import com.progetto.dto.ResponseFromApi;
import com.progetto.dto.TransactionDto;
import com.progetto.exception.GenericError;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.TransactionBankRepo;
import com.progetto.service.ItransactionBankService;
import com.progetto.utils.Crypthography;

@Service
public class TransactionBankServiceImpl implements ItransactionBankService {

	private final Logger logger = LoggerFactory.getLogger(TransactionBankServiceImpl.class);

	@Autowired
	TransactionBankRepo transactionRepo;

	@Autowired
	ClientAccountBankRepo bankAccountRepo;

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi save(TransactionDto transactionDTO) throws GenericError {
		try {

			if (transactionDTO.getTransactionTime() == null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
				dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				transactionDTO
						.setTransactionTime(new Timestamp(dateFormat.parse(dateFormat.format(new Date())).getTime()));
			}
		
			Transaction transaction = ConversionTransactionOperation
					.fromTransactionDtoToTransactionDomain(transactionDTO);
			transaction.setId(new StringBuilder(Uuid.randomUuid().toString()).append(String.valueOf(new Date().getTime())).toString());
			BankAccount ba = bankAccountRepo.findByIban(Crypthography.encrypt(transactionDTO.getIban()));
			transaction.setBankAccount(ba);
			transactionRepo.save(transaction);
			transactionDTO.setPropetaryOfIban(new StringBuilder(ba.getClientBank().getFirstName()).append(" ")
					.append(ba.getClientBank().getLastName()).toString());
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR IN SAVE METHOD FOR TRANSACTION ENTITY. ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return ConversionTransactionOperation.fromTransactionToResponseFromApi(transactionDTO, "Transaction saved",
				HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi findAll() throws GenericError {
		List<Transaction> list;
		try {
			list = transactionRepo.findAll();
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR IN FIND ALL METHOD FOR TRANSACTION. ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return ConversionTransactionOperation.fromTransactionToResponseFromApitoList(list);
	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi delete(String id) throws GenericError {
		Transaction transaction = transactionRepo.findById(id).orElseThrow(() -> new GenericError(
				new StringBuilder("Transaction with id ").append(id).append(" not present in db.").toString()));
		transactionRepo.delete(transaction);
		return new ResponseFromApi(new StringBuilder("Transaction id: ").append(id).append(" deleted").toString(),
				HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi findByIban(String iban) throws GenericError {
		List<Transaction> transactions;
		try {
			BankAccount ba = bankAccountRepo.findByIban(Crypthography.encrypt(iban));
			transactions = transactionRepo.findTransactionByBankAccounId(ba.getId());
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("Iban insert ").append(iban).append(" not present in the system");
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		return ConversionTransactionOperation.fromTransactionToResponseFromApitoList(transactions);
	}

}
