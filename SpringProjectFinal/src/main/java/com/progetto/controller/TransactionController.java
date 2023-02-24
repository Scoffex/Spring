package com.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.dto.ResponseFromApi;
import com.progetto.dto.TransactionDto;
import com.progetto.exception.GenericError;
import com.progetto.service.ItransactionBankService;
import com.progetto.utils.Constants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/spring/transaction")
@Validated
public class TransactionController {

	@Autowired
	ItransactionBankService transactionService;

	@PostMapping(value = Constants.SAVE_CLIENT_BANK_MAPPING, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFromApi save(@RequestBody @Valid TransactionDto dto) throws GenericError {
		return transactionService.save(dto);
	}

	@GetMapping(value = Constants.GET_ALL_BANK_ACCOUNT_MAPPING, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFromApi findAll() throws GenericError {
		return transactionService.findAll();
	}

	@DeleteMapping(value = Constants.TRANSACTION_DELETE_TRANSACTION_MAPPING)
	public ResponseFromApi delete(@PathVariable int id) throws GenericError {
		return transactionService.delete(id);

	}

	@GetMapping(value = Constants.TRANSACTION_FIND_BY_IBAN_TRANSACTION_MAPPING)
	public ResponseFromApi findByEmail(
			@PathVariable @Pattern(regexp = Constants.IBAN_REGEX, message = Constants.INVALID_IBAN) String iban)
			throws GenericError {
		return transactionService.findByIban(iban);
	}

}
