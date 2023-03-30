package com.progetto.dto;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.progetto.utils.Constants;
import com.progetto.utils.ParamReader;
import com.progetto.validators.DateCustomConstraint;
import com.progetto.validators.EmailCustomConstraint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClientBankDto {

	@Autowired
	ParamReader param;

	@NotBlank(message = Constants.NAME_NOT_BLANK)
	String name;

	@NotBlank(message = Constants.SURNAME_NOT_BLANK)
	String surname;

	@DateCustomConstraint
	LocalDate birth;

	@NotBlank(message = "PASSWORD CAN'T BE NULL OR EMPTY")
	@Pattern(regexp = Constants.REGEX_PASSWORD, message = Constants.ERROR_PASSWORD_MESSAGE)
	String password;

	@NotBlank(message = "EMAIL CAN'T BE NULL OR EMPTY")
	@Pattern(regexp = Constants.REGEX_EMAIL, message = Constants.FORMAT_EMAIL_ERROR)
	@EmailCustomConstraint
	String email;
	
	String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ParamReader getParam() {
		return param;
	}

	public void setParam(ParamReader param) {
		this.param = param;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
