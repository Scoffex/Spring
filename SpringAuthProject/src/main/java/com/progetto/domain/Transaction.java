package com.progetto.domain;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.progetto.utils.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_client")
public class Transaction {
	@Id
	String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "bank_account_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	BankAccount bankAccount;

	@Column(nullable = false)
	String operation; 

	@Column(nullable = false)
	double amount;

	@Column(nullable = false)
	String status;

	@Column(nullable = false)
	@DateTimeFormat(pattern = Constants.PATTERN_DATE)
	Date transactionTime;

	@Column(nullable = false)
	String atmCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
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
		return "Transaction [id=" + id + ", bankAccount=" + bankAccount + ", operation=" + operation + ", amount="
				+ amount + ", status=" + status + ", transactionTime=" + transactionTime + ", atmCode=" + atmCode + "]";
	}

	
	

}
