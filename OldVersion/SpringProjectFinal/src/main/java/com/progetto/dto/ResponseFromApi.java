package com.progetto.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.progetto.domain.ClientBank;

public class ResponseFromApi {

	List<ClientBank> listOfuser;

	ClientBank singleClient;

	List<BankAccountClientDto> bankAccount;

	BankAccountClientDto bankAccountSingle;

	List<TransactionDto> listOfTransaction;

	TransactionDto singleTransaction;

	OperationDto operationDto;

	String message;

	HttpStatus statudCode;

	public ResponseFromApi(String message, HttpStatus statusCode) {
		this.message = message;
		this.statudCode = statusCode;
	}

	public ResponseFromApi(String message) {
		this.message = message;
	}

	public ResponseFromApi() {

	}

	public List<ClientBank> getListOfuser() {
		return listOfuser;
	}

	public void setListOfuser(List<ClientBank> listOfuser) {
		this.listOfuser = listOfuser;
	}

	public ClientBank getSingleClient() {
		return singleClient;
	}

	public void setSingleClient(ClientBank singleClient) {
		this.singleClient = singleClient;
	}

	public List<BankAccountClientDto> getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(List<BankAccountClientDto> bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BankAccountClientDto getBankAccountSingle() {
		return bankAccountSingle;
	}

	public void setBankAccountSingle(BankAccountClientDto bankAccountSingle) {
		this.bankAccountSingle = bankAccountSingle;
	}

	public List<TransactionDto> getListOfTransaction() {
		return listOfTransaction;
	}

	public void setListOfTransaction(List<TransactionDto> listOfTransaction) {
		this.listOfTransaction = listOfTransaction;
	}

	public TransactionDto getSingleTransaction() {
		return singleTransaction;
	}

	public void setSingleTransaction(TransactionDto singleTransaction) {
		this.singleTransaction = singleTransaction;
	}

	public OperationDto getOperationDto() {
		return operationDto;
	}

	public void setOperationDto(OperationDto operationDto) {
		this.operationDto = operationDto;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatudCode() {
		return statudCode;
	}

	public void setStatudCode(HttpStatus statudCode) {
		this.statudCode = statudCode;
	}

	@Override
	public String toString() {
		return "ResponseFromApi [listOfuser=" + listOfuser + ", singleClient=" + singleClient + ", bankAccount="
				+ bankAccount + ", bankAccountSingle=" + bankAccountSingle + ", listOfTransaction=" + listOfTransaction
				+ ", singleTransaction=" + singleTransaction + ", operationDto=" + operationDto + ", message=" + message
				+ ", statudCode=" + statudCode + "]";
	}

}