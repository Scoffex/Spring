package com.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.service.IverificationToken;
import com.progetto.utils.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/spring/verification")
public class VerificationTokenController {

	@Autowired
	IverificationToken verificationToken;
	
	@GetMapping(value = Constants.VERIFICATION_TOKEN)
	public ResponseFromApi verification(@PathVariable @NotBlank String token, HttpServletRequest request) throws GenericError {
		return verificationToken.confrimCreationAccount(token, request);
	}
	
	
}
