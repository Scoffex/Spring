package com.progetto.dto;

import java.time.LocalDate;

public class BankAccountClientDto {

	String iban;
	String currency;
	LocalDate creationDate;
	double amount;
	String propetary;
	
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPropetary() {
		return propetary;
	}
	public void setPropetary(String propetary) {
		this.propetary = propetary;
	}
	
	
	
	
}
