package com.progetto.conversion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.progetto.domain.Transaction;
import com.progetto.dto.OperationDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.dto.TransactionDto;
import com.progetto.utils.Constants;


public class ConversionTransactionOperation {

	private ConversionTransactionOperation() {}

	public static Transaction fromTransactionDtoToTransactionDomain(TransactionDto transactionDTO) {
		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setAtmCode(transactionDTO.getAtmCode());
		transaction.setOperation(transactionDTO.getOperation());
		transaction.setStatus(transactionDTO.getStatus());
		transaction.setTransactionTime(transactionDTO.getTransactionTime());
		return transaction;
	}

	public static TransactionDto fromTransactionDomainToTransactionDto(Transaction transaction) {
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setAmount(transaction.getAmount());
		transactionDto.setAtmCode(transaction.getAtmCode());
		transactionDto.setOperation(transaction.getOperation());
		transactionDto.setStatus(transaction.getStatus());
	
		transactionDto.setTransactionTime(transaction.getTransactionTime());
		transactionDto.setPropetaryOfIban(
				new StringBuilder().append(transaction.getBankAccount().getClientBank().getFirstName()).append(" ")
						.append(transaction.getBankAccount().getClientBank().getLastName()).toString());
		return transactionDto;
	}

	public static ResponseFromApi fromTransactionToResponseFromApi(TransactionDto transaction, String message,
			HttpStatus status) {
		ResponseFromApi response = new ResponseFromApi();
		response.setSingleTransaction(transaction);
		response.setMessage(message);
		response.setStatudCode(status);
		return response;
	}

	public static ResponseFromApi fromTransactionToResponseFromApitoList(List<Transaction> transactionList) {
		ResponseFromApi response = new ResponseFromApi();
		List<TransactionDto> list = new ArrayList<>();
		for (Transaction transaction : transactionList) {
			list.add(ConversionTransactionOperation.fromTransactionDomainToTransactionDto(transaction));
		}
		response.setListOfTransaction(list);
		return response;

	}

	public static TransactionDto fromOperationToTransactionDto(OperationDto operation) {
		TransactionDto transDto = new TransactionDto();
		transDto.setAmount(operation.getAmount());
		transDto.setAtmCode(operation.getAtmCode());
		transDto.setIban(operation.getIban());
		transDto.setOperation(operation.getOperation().toUpperCase());
		transDto.setStatus(Constants.CHECK);
		transDto.setTransactionTime(operation.getOperationTime());
		return transDto;
	}

}
