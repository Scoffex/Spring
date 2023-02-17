package com.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.dto.OperationConfirmDto;
import com.progetto.dto.OperationDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.service.IoperationService;
import com.progetto.utils.Constants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/spring/operation")
@Validated
public class OperationController {

	@Autowired
	IoperationService operationService;

	@GetMapping(value = Constants.BALANCE_OPERATION_MAPPING)
	public ResponseFromApi balanceSingle(@PathVariable @Pattern(regexp = Constants.IBAN_REGEX, message = Constants.INVALID_IBAN) String iban) throws GenericError {
		return operationService.balance(iban);
	}

	@GetMapping(value = Constants.BALANCE_ALL_OPERATION_MAPPING)
	public ResponseFromApi balanceAll(@PathVariable  @Pattern(regexp = Constants.PAN_CODD_REGEX, message = Constants.INVALID_PAN_CODE) String panCode) throws GenericError {
		return operationService.balanceAllAccount(panCode);
	}
	
	@PostMapping(value = Constants.TRANSACTION_CHECK_OPERATION_MAPPING)
	public ResponseFromApi checkTransaction(@RequestBody @Valid OperationDto operation) throws GenericError {
		return operationService.operationCheck(operation);
				
	}
	
	@PostMapping(value = Constants.TRANSACTION_CONFIRM_OPERATION_MAPPING)
	public ResponseFromApi confirmTransaction(@RequestBody @Valid OperationConfirmDto operation) throws GenericError {
		return operationService.operationConfirm(operation);
				
	}

	
}
