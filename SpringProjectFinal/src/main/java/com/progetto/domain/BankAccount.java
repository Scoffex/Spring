package com.progetto.domain;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.progetto.utils.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = Constants.TABLE_NAME_BANK_ACCOUNT)
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false)
    String iban;
 
    @Column(nullable = false)
    double amount;

    @Column(nullable = false) 
    String currency;
    
    @Column(nullable = false)
    @DateTimeFormat(pattern = Constants.PATTERN_DATE)
    LocalDate dateCreationAccount;
     
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = Constants.COLUMN_NAME_BANK_ACCOUNT_CLIENT, nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    ClientBank clientBank;
    
   
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

		
	public LocalDate getDateCreationAccount() {
		return dateCreationAccount;
	}

	public void setDateCreationAccount(LocalDate dateCreationAccount) {
		this.dateCreationAccount = dateCreationAccount;
	}

	public ClientBank getClientBank() {
		return clientBank;
	}

	public void setClientBank(ClientBank clientBank) {
		this.clientBank = clientBank;
	}

	
	
	

	
	
    
    
}
