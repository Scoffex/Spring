package com.progetto.service_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progetto.domain.AdminBank;
import com.progetto.dto.AdminBankDto;
import com.progetto.dto.ResponseFromApi;
import com.progetto.exception.GenericError;
import com.progetto.repository.AdminBankRepo;
import com.progetto.service.IadminBankService;
import com.progetto.utils.Constants;

@Service
public class AdminBankServiceImpl implements IadminBankService {

	private final Logger logger = LoggerFactory.getLogger(AdminBankServiceImpl.class);

	@Autowired
	private AdminBankRepo adminRepo;

	@Autowired
	private PasswordEncoder encode;
	

	
	@Override
	@Transactional(rollbackFor = GenericError.class)
	public ResponseFromApi save(AdminBankDto admin) throws GenericError {
		String message;
		HttpStatus status;
		try {
			AdminBank adminBank = new AdminBank(admin.getName(), admin.getSurname(), encode.encode(admin.getPassword()));
			adminRepo.save(adminBank);
			message = new StringBuilder("ADMIN CORRECTLY CREATED. YOUR IDENTIFICATIV ARE: ").append(adminBank.getId()).toString();
			status = HttpStatus.OK;
		} catch (Exception e) {
			message  = new StringBuilder(
					Constants.ERROR_ADMIN_SAVE).append(e).toString();
			logger.error(message);
		
			throw new GenericError(message);
		}

		return new ResponseFromApi(message, status);

	}

}