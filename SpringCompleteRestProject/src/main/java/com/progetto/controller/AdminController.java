package com.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.dto.AdminBankDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.service.IadminBankService;
import com.progetto.utils.Constants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/spring/admin") 
@Validated
public class AdminController {

	
	
	@Autowired
	IadminBankService adminService;


	
	@PostMapping(value = Constants.SAVE_CLIENT_BANK_MAPPING, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFromApi save(@RequestBody @Valid AdminBankDto admin) throws GenericError {
		return adminService.save(admin);
	}

	
}
