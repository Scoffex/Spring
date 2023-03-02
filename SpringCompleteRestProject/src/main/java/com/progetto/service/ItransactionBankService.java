package com.progetto.service;

import com.progetto.dto.ResponseFromApi;
import com.progetto.dto.TransactionDto;
import com.progetto.exception.GenericError;

public interface ItransactionBankService {

    public ResponseFromApi save(TransactionDto dto) throws GenericError;
    public ResponseFromApi findAll() throws GenericError;
    public ResponseFromApi delete(String id) throws GenericError;
    public ResponseFromApi findByIban(String iban) throws GenericError;
}
