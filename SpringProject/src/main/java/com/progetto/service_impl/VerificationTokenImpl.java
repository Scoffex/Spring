package com.progetto.service_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progetto.domain.ClientBank;
import com.progetto.domain.VerificationToken;
import com.progetto.exception.GenericError;
import com.progetto.repository.VerificationTokenRepo;
import com.progetto.service.IverificationToken;

@Service
public class VerificationTokenImpl implements IverificationToken {

	private final Logger logger = LoggerFactory.getLogger(VerificationTokenImpl.class);

	@Autowired
	private VerificationTokenRepo repo;
	
	@Override
	@Transactional(rollbackFor = GenericError.class)
	public void save(ClientBank client, String token) throws GenericError {
		try {
			logger.info("CLIENT" + client.toString());
			logger.info("TOKEN" + token);
			VerificationToken verificationToken = new  VerificationToken(client, token);
			logger.info("VERIFICATION TOKEN GENERATE" + token.toString());
			repo.save(verificationToken);
		} catch (Exception e) {
			throw new GenericError("IMPOSSIBLE SAVE THE TOKEN. ERROR: " + e.toString());
		}
	}
	
	
}
