package com.progetto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.dto.Credential;
import com.progetto.security.JWTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/spring/jwt")
public class LogInController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JWTokenService jwtServie;

	@Autowired
	AuthenticationManager authMenager;

	@PostMapping(value = "/auth")
	public String jwtTokenGenerator(@RequestBody @Valid Credential credential) {
		authMenager.authenticate(
				new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword()));
		return jwtServie.generateToken(credential.getUsername());

	}

}
