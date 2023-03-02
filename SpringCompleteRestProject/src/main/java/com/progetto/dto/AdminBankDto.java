package com.progetto.dto;

import com.progetto.utils.Constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AdminBankDto {

	@NotBlank(message = "NAME CAN'T BE EMPTY OR NULL")
	String name;
	
	@NotBlank(message = "SURNAME CAN'T BE EMPTY OR NULL")
	String surname;
	
	@NotBlank(message = "PASSWORD CAN'T BE NULL OR EMPTY")
	@Pattern(regexp = Constants.REGEX_PASSWORD, message = Constants.ERROR_PASSWORD_MESSAGE)
	String password;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
