package com.progetto.service;

import com.progetto.domain.ClientBank;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;

import jakarta.servlet.http.HttpServletRequest;

public interface IverificationToken {

    public void save(ClientBank client, String token) throws GenericError;
    public ResponseFromApi confrimCreationAccount(String token, HttpServletRequest request) throws GenericError;
   
	
}
