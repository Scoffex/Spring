//package com.progetto.utils;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//
//import com.progetto.domain.BankAccount;
//import com.progetto.domain.ClientBank;
//import com.progetto.domain.Transaction;
//import com.progetto.dto.BankAccountClientDto;
//import com.progetto.dto.ClientBankDto;
//import com.progetto.dto.OperationDto;
//import com.progetto.dto.PanCodeInfo;
//import com.progetto.dto.ResponseFromApi;
//import com.progetto.dto.TransactionDto;
//
//public class Conversion {
//
//	public static ClientBank fromDtoToDomain(ClientBankDto usdto) {
//		ClientBank user = new ClientBank();
//		user.setBorningDate(usdto.getBirth());
//		user.setEmail(usdto.getEmail());
//		user.setFirstName(usdto.getName());
//		user.setLastName(usdto.getSurname());
//		user.setPassword(usdto.getPassword());
//		return user;
//	}
//
//	public static ClientBankDto fromDomainToDto(ClientBank user) {
//		ClientBankDto usdto = new ClientBankDto();
//		usdto.setBirth(user.getBorningDate());
//		usdto.setEmail(user.getEmail());
//		usdto.setName(user.getFirstName());
//		usdto.setPassword(user.getPassword());
//		usdto.setSurname(user.getLastName());
//		return usdto;
//	}
//
//	public static BankAccountClientDto fromDomainBankToDto(BankAccount ba) {
//		BankAccountClientDto dtoBank = new BankAccountClientDto();
//		dtoBank.setAmount(ba.getAmount());
//		dtoBank.setCreationDate(ba.getDateCreationAccount());
//		dtoBank.setCurrency(ba.getCurrency());
//		dtoBank.setIban(ba.getIban());
//		dtoBank.setPropetary(new StringBuilder(ba.getClientBank().getFirstName()).append(" ")
//				.append(ba.getClientBank().getLastName()).toString());
//		return dtoBank;
//	}
//
//	public static BankAccount fromDtoBankToDomain(BankAccountClientDto dto) {
//		BankAccount bankAccount = new BankAccount();
//		bankAccount.setAmount(dto.getAmount());
//		bankAccount.setCurrency(dto.getCurrency());
//		bankAccount.setDateCreationAccount(dto.getCreationDate());
//		bankAccount.setIban(dto.getIban());
//		return bankAccount;
//	}
//
//	public static List<BankAccountClientDto> fromListDomainBanotoListDtoBank(List<BankAccount> bankList) {
//		List<BankAccountClientDto> list = new ArrayList<>();
//		for (BankAccount bank : bankList) {
//			list.add(fromDomainBankToDto(bank));
//		}
//		return list;
//	}
//
//	public static ResponseFromApi fromDomainToResponse(ClientBank clientBank, List<BankAccount> ba, String message,
//			HttpStatus statuCode) {
//		ResponseFromApi response = new ResponseFromApi();
//		response.setEmail(clientBank.getEmail());
//		response.setFirstName(clientBank.getFirstName());
//		response.setLastName(clientBank.getLastName());
//		response.setPassword(clientBank.getPassword());
//		response.setPanCodeInfo(fromDomainToPanCodeInfo(clientBank));
//		response.setBankAccount(fromListDomainBanotoListDtoBank(ba));
//		response.setStatudCode(statuCode);
//		response.setMessage(message);
//		return response;
//	}
//
//	private static PanCodeInfo fromDomainToPanCodeInfo(ClientBank client) {
//		PanCodeInfo panCodeInfo = new PanCodeInfo();
//		panCodeInfo.setCreationDate(client.getCreationDate());
//		panCodeInfo.setExpriationDate(client.getExpirationDate());
//		panCodeInfo.setPanCode(client.getPanCode());
//		return panCodeInfo;
//	}
//
//	public static Transaction fromTransactionDtoToTransactionDomain(TransactionDto transactionDTO) {
//		Transaction transaction = new Transaction();
//		transaction.setAmount(transactionDTO.getAmount());
//		transaction.setAtmCode(transactionDTO.getAtmCode());
//		transaction.setOperation(transactionDTO.getOperation());
//		transaction.setStatus(transactionDTO.getStatus());
//		transaction.setTransactionTime(transactionDTO.getTransactionTime());
//		return transaction;
//	}
//
//	public static TransactionDto fromTransactionDomainToTransactionDto(Transaction transaction) {
//		TransactionDto transactionDto = new TransactionDto();
//		transactionDto.setAmount(transaction.getAmount());
//		transactionDto.setAtmCode(transaction.getAtmCode());
//		transactionDto.setOperation(transaction.getOperation());
//		transactionDto.setStatus(transaction.getStatus());
//		transactionDto.setTransactionTime(new Date());
//		transactionDto.setPropetaryOfIban(
//				new StringBuilder().append(transaction.getBankAccount().getClientBank().getFirstName()).append(" ")
//						.append(transaction.getBankAccount().getClientBank().getLastName()).toString());
//		return transactionDto;
//	}
//
//	public static ResponseFromApi fromTransactionToResponseFromApi(TransactionDto transaction, String message,
//			HttpStatus status) {
//		ResponseFromApi response = new ResponseFromApi();
//		response.setSingleTransaction(transaction);
//		response.setMessage(message);
//		response.setStatudCode(status);
//		return response;
//	}
//
//	public static ResponseFromApi fromTransactionToResponseFromApitoList(List<Transaction> transactionList) {
//		ResponseFromApi response = new ResponseFromApi();
//		List<TransactionDto> list = new ArrayList<>();
//		for (Transaction transaction : transactionList) {
//			list.add(Conversion.fromTransactionDomainToTransactionDto(transaction));
//		}
//		response.setListOfTransaction(list);
//		return response;
//
//	}
//
//	public static TransactionDto fromOperationToTransactionDto(OperationDto operation) {
//		TransactionDto transDto = new TransactionDto();
//		transDto.setAmount(operation.getAmount());
//		transDto.setAtmCode(operation.getAtmCode());
//		transDto.setIban(operation.getIban());
//		transDto.setOperation(operation.getOperation());
//		transDto.setStatus(Constants.CHECK);
//		transDto.setTransactionTime(operation.getOperationTime());
//		return transDto;
//	}
//
//}
