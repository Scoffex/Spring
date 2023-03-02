package com.progetto.dto;

import java.util.Date;

import com.progetto.utils.Constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class TransactionDto {

	@NotBlank(message = "IBAN CAN'T BE NULL OR EMPTY")
	@Pattern(regexp = Constants.IBAN_REGEX, message = Constants.INVALID_IBAN)
	String iban;
	
	String propetaryOfIban;
	
	@NotBlank(message = "OPERATION CAN'T BE NULL OR EMPTY")
	String operation; 

	@NotNull
	double amount;

	@NotBlank
	String status;

	Date transactionTime;

	@NotBlank(message = "ATMCODE CAN'T BE NULL OR EMPTY")
	String atmCode;

	
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getPropetaryOfIban() {
		return propetaryOfIban;
	}

	public void setPropetaryOfIban(String propetaryOfIban) {
		this.propetaryOfIban = propetaryOfIban;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getAtmCode() {
		return atmCode;
	}

	public void setAtmCode(String atmCode) {
		this.atmCode = atmCode;
	}

	@Override
	public String toString() {
		return "TransactionDto [iban=" + iban + ", propetaryOfIban=" + propetaryOfIban + ", operation=" + operation
				+ ", amount=" + amount + ", status=" + status + ", transactionTime=" + transactionTime + ", atmCode="
				+ atmCode + "]";
	}
	
	
	
	
}
