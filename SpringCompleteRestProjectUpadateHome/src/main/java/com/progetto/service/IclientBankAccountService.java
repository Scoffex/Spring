package com.progetto.service;

import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.dto.BankAccountClientDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;

public interface IclientBankAccountService {

    public ResponseFromApi save(String panCode) throws GenericError;
    public ResponseFromApi findAll() throws GenericError;
    public ResponseFromApi delete(String iban) throws GenericError;
    public BankAccount createBankAccount(ClientBank client) throws GenericError;
    public ResponseFromApi findByIban(String iban) throws GenericError;
    public ResponseFromApi update (BankAccountClientDto dto, String iban) throws GenericError;
    public ResponseFromApi findByPanCode(String panCode) throws GenericError;
	
}
