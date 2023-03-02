package com.progetto.service;

import com.progetto.dto.AdminBankDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;

public interface IadminBankService {

    public ResponseFromApi save(AdminBankDto admin) throws GenericError;
    
	
}
