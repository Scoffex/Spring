package com.progetto.dto;

import java.sql.Timestamp;

import com.progetto.utils.Constants;
import com.progetto.validators.IbanCustomConstraint;
import com.progetto.validators.WithdrawlCustomConstraint;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@WithdrawlCustomConstraint(amount = "amount", iban = "iban", operation = "operation")
public class OperationDto {

	
	@NotBlank(message = "IBAN CAN'T BE NULL EMPTY OR NULL")
	@IbanCustomConstraint(message = "IBAN INSERT NOT PRESENT IN THE SYSTEM")
	String iban;
	
	@NotNull
	@Min(value = 5)
	@Max(value = 25000)
	double amount;
	
	@Pattern(regexp = "(?i)^(withdrawl|deposit)$", message = "OPERATION SELECTED NOT VALID")
	String operation;
	
	
	String status;
	@NotBlank(message = "ATM CODE CAN'T BE NULL OR EMPTY")
	@Pattern(regexp = Constants.PAN_CODD_REGEX, message = "INVALID ATM CODE")
	String atmCode;
	
	Timestamp operationTime;
	public OperationDto() {
		
	}
	
	public OperationDto(String atmCode, Timestamp operationTime, String iban, String operation) {
		this.atmCode = atmCode;
		this.operationTime = operationTime;
		this.iban = iban;
		this.operation = operation;
	}

	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
	public String getAtmCode() {
		return atmCode;
	}
	public void setAtmCode(String atmCode) {
		this.atmCode = atmCode;
	}
	
	
	public Timestamp getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}
	
	@Override
	public String toString() {
		return "OperationDto [iban=" + iban + ", amount=" + amount + ", operation=" + operation
				+ ", status=" + status + ", atmCode=" + atmCode + ", operationTime=" + operationTime + "]";
	}
	
	
	
}
