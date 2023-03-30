package com.progetto.service_impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progetto.conversion.ConversionTransactionOperation;
import com.progetto.domain.BankAccount;
import com.progetto.domain.Transaction;
import com.progetto.dto.OperationConfirmDto;
import com.progetto.dto.OperationDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.repository.TransactionBankRepo;
import com.progetto.service.IclientBankAccountService;
import com.progetto.service.IoperationService;
import com.progetto.utils.Constants;

@Service
public class OperationServiceImpl implements IoperationService {

	private final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	@Autowired
	TransactionBankServiceImpl transactionService;

	@Autowired
	IclientBankAccountService bankService;

	@Autowired
	ClientAccountBankRepo bankRepo;

	@Autowired
	TransactionBankRepo transactionRepo;

	@Override
	public ResponseFromApi balance(String iban) throws GenericError {
		return bankService.findByIban(iban);

	}

	@Override
	public ResponseFromApi balanceAllAccount(String panCode) throws GenericError {
		return bankService.findByPanCode(panCode);

	}

	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi operationCheck(OperationDto operation) throws GenericError {
		ResponseFromApi response = new ResponseFromApi();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		try {
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			operation.setOperationTime(new Timestamp(dateFormat.parse(dateFormat.format(new Date())).getTime()));
			transactionService.save(ConversionTransactionOperation.fromOperationToTransactionDto(operation));
			response.setOperationDto(operation);
			response.setMessage(new StringBuilder("OPERATION OF ").append(operation.getOperation().toUpperCase())
					.append(" ARE CHECK").toString());
			response.setStatudCode(HttpStatus.OK);

		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR DURING OPERATION METHOD, ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		logger.info(response.toString());
		return response;
	}

	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi operationConfirm(OperationConfirmDto operation) throws GenericError {
		ResponseFromApi response = new ResponseFromApi();
		try {
			logger.info(operation.toString());
			String atmCode = operation.getAtmCode();
			Timestamp timeOperation = operation.getOperationTime();
			Transaction transaction = transactionRepo.findByAtmCodeAndTimeStamp(atmCode, timeOperation);
			if (Constants.CHECK.equalsIgnoreCase(transaction.getStatus())) {
				String iban = transaction.getBankAccount().getIban();
				double amountOfTransaction = transaction.getAmount();
				BankAccount ba = bankRepo.findByIban(iban);
				if (Constants.DEPOSIT.equalsIgnoreCase(transaction.getOperation())) {
					ba.setAmount(ba.getAmount() + amountOfTransaction);
				} else {
					ba.setAmount(ba.getAmount() - amountOfTransaction);
				}
				OperationDto operationDto = new OperationDto(atmCode, timeOperation, iban, transaction.getOperation(), amountOfTransaction);
				logger.info(new StringBuilder("OPERATION DTO CREATE: ").append(operationDto.toString()).toString());

				response.setOperationDto(operationDto);
				response.setMessage(new StringBuilder("OPERATION OF ").append(operationDto.getOperation().toUpperCase())
						.append(" ARE CONFIRM").toString());
				transaction.setStatus(Constants.CONFIRM);
			} else {
				throw new GenericError(new StringBuilder("TRANSACTION N.").append(transaction.getId())
						.append(" ALREADY CONFIRMED").toString());
			}
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder("ERROR IN CONFIRM OPERATION METHOD. ERROR: ").append(e);
			logger.error(sb.toString());
			throw new GenericError(sb.toString());
		}
		logger.info(response.toString());
		logger.info("{}", new ResponseFromApi());
		return response;
	}
}
