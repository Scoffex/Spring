package com.progetto.domain;

import java.util.Date;

import org.apache.kafka.common.Uuid;
import org.springframework.format.annotation.DateTimeFormat;

import com.progetto.utils.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
 
@Entity
@Table(name = "bank_admin")
@Data
public class AdminBank {

	@Id
	@Column(name = "admin_code", length = 100)
	String id;

	@Column(nullable = false,  length = 50)
	String firstName;

	@Column(nullable = false,  length = 70)
	String lastName;
	
	@Column(nullable = false, length = 100)
	String password;

	
	@Column(nullable = false, name = "creation_admin_date")
	@DateTimeFormat(pattern = Constants.PATTERN_DATE)
	Date creationDate;

	public AdminBank() {}
	public AdminBank(String firstName, String lastName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.creationDate = new Date();
		this.id = new StringBuilder(Constants.ADMIN_CODE_STARTING).append(Uuid.randomUuid().toString()).append(creationDate.getTime()).toString();
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	@Override
	public String toString() {
		return "AdminBank [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", creationDate=" + creationDate + "]";
	}
	
	
	
	
	
	

}
