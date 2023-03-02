package com.progetto.dto;

import com.progetto.validators.CredentialCustomConstraint;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;



public class Credential {

	@NotBlank(message = "USERNAME CAN'T BE NULL")
	@CredentialCustomConstraint
	public String username;
	
	@NotBlank(message = "USERNAME CAN'T BE NULL")
	public String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
