package com.progetto.service;

import com.progetto.domain.ClientBank;
import com.progetto.exception.GenericError;

public interface IverificationToken {

    public void save(ClientBank client, String token) throws GenericError;
   
	
}
