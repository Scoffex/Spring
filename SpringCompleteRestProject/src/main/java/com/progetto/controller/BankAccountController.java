package com.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.dto.BankAccountClientDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.kafka.KafkaRender;
import com.progetto.service.IclientBankAccountService;
import com.progetto.utils.Constants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/spring/account")
public class BankAccountController {

	@Autowired
	KafkaRender kafkaRender;

	@Autowired
	IclientBankAccountService bankAccountService;

	@PostMapping(value = Constants.SAVE_BANK_ACCOUNT_MAPPING, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFromApi save(
			@PathVariable @Pattern(regexp = Constants.PAN_CODD_REGEX, message = Constants.INVALID_PAN_CODE) String panCode)
			throws GenericError {
		ResponseFromApi response = bankAccountService.save(panCode);
		kafkaRender.printMessage(response);
		return response;
	}

	@DeleteMapping(value = Constants.DELETE_BANK_ACCOUNT_MAPPING, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFromApi delete(
			@PathVariable @Pattern(regexp = Constants.IBAN_REGEX, message = Constants.INVALID_IBAN) String iban)
			throws GenericError {
		ResponseFromApi response = bankAccountService.delete(iban);
		kafkaRender.printMessage(response);
		return response;
	}

	@GetMapping(value = Constants.GET_ALL_BANK_ACCOUNT_MAPPING, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFromApi findAll() throws GenericError {
		ResponseFromApi response = bankAccountService.findAll();
		kafkaRender.printMessage(response);
		return response;
	}

	@GetMapping(value = Constants.GET_IBAN_BANK_ACCOUNT_MAPPING, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseFromApi findByIban(
			@PathVariable @Pattern(regexp = Constants.IBAN_REGEX, message = Constants.INVALID_IBAN) String iban)
			throws GenericError {
		ResponseFromApi response = bankAccountService.findByIban(iban);
		kafkaRender.printMessage(response);
		return response;
	}

	@PutMapping(value = Constants.UPDATE_BANK_ACCOUNT_MAPPING)
	public ResponseFromApi update(
			@PathVariable @Pattern(regexp = Constants.IBAN_REGEX, message = Constants.INVALID_IBAN) String iban,
			@RequestBody @Valid BankAccountClientDto dto) throws GenericError {
		ResponseFromApi response = bankAccountService.update(dto, iban);
		kafkaRender.printMessage(response);
		return response;
	}

}
