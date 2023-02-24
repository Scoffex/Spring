package com.progetto.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.progetto.utils.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "bank_anagrafic_client")
public class ClientBank {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@Column(nullable = false,  length = 50)
	String firstName;

	@Column(nullable = false,  length = 70)
	String lastName;

	@Column(nullable = false, length = 100, unique = true)
	String email;

	@Column(nullable = false, length = 100)
	String password;

	@Column(nullable = false)
	@DateTimeFormat(pattern = Constants.PATTERN_DATE)
	LocalDate borningDate;

	@Column(nullable = false, length = 100, unique = true)
	String panCode;
	 
	@Column(nullable = false, length = 100)
	String status;
	
	@Column(nullable = false, name = "creation_pancode_date")
	@DateTimeFormat(pattern = Constants.PATTERN_DATE)
	LocalDate creationDate;
	
	@Column(nullable = false, name = "expiration_pancode_date")
	@DateTimeFormat(pattern = Constants.PATTERN_DATE)
	LocalDate expirationDate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "clientBank", targetEntity = BankAccount.class)
    List<BankAccount> bankAccount; 
	
	
	
	@Override
	public String toString() {
		return "ClientBank [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", borningDate=" + borningDate + ", panCode=" + panCode + ", status="
				+ status + ", creationDate=" + creationDate + ", expirationDate=" + expirationDate + " ]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BankAccount> getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(List<BankAccount> bankAccount) {
		this.bankAccount = bankAccount;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBorningDate() {
		return borningDate;
	}

	public void setBorningDate(LocalDate borningDate) {
		this.borningDate = borningDate;
	}

	public String getPanCode() {
		return panCode;
	}

	public void setPanCode(String panCode) {
		this.panCode = panCode;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	
	
	

}
