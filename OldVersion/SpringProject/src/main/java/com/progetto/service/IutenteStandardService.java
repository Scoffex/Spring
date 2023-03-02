package com.progetto.service;

import java.util.List;

import com.progetto.dto.ClientBankDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;

import jakarta.servlet.http.HttpServletRequest;

public interface IutenteStandardService {

    public ResponseFromApi save(ClientBankDto user, String panCode, HttpServletRequest request) throws GenericError;
    public List<ResponseFromApi> findAll() throws GenericError;
    public ResponseFromApi delete(String panCode) throws GenericError;
    public ResponseFromApi update(ClientBankDto user, String panCode) throws GenericError;
    public ResponseFromApi findByEmail(String email) throws GenericError;
}
