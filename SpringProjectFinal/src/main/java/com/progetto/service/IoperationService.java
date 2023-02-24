package com.progetto.service;

import com.progetto.dto.OperationConfirmDto;
import com.progetto.dto.OperationDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;

public interface IoperationService {

	public ResponseFromApi balance(String iban) throws GenericError;

	public ResponseFromApi balanceAllAccount(String panCode) throws GenericError;
	
	public ResponseFromApi operationCheck(OperationDto operation) throws GenericError;
	
	public ResponseFromApi operationConfirm(OperationConfirmDto operation) throws GenericError;
	
}
