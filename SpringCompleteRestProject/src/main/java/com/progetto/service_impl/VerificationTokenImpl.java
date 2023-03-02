package com.progetto.service_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progetto.domain.ClientBank;
import com.progetto.domain.VerificationToken;
import com.progetto.dto.ResponseFromApi;
import com.progetto.event.RegistrationCompleteEvent;
import com.progetto.exception.GenericError;
import com.progetto.repository.VerificationTokenRepo;
import com.progetto.service.IverificationToken;
import com.progetto.utils.Constants;
import com.progetto.utils.GenericMethod;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class VerificationTokenImpl implements IverificationToken {

	private final Logger logger = LoggerFactory.getLogger(VerificationTokenImpl.class);

	@Autowired
	private VerificationTokenRepo repo;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Override
	@Transactional(rollbackFor = GenericError.class)
	public void save(ClientBank client, String token) throws GenericError {
		try {
			VerificationToken verificationToken = new VerificationToken(client, token);
			repo.save(verificationToken);
		} catch (Exception e) {
			throw new GenericError("IMPOSSIBLE SAVE THE TOKEN. ERROR: " + e.toString());
		}

	}

	@Override
	@Transactional
	public ResponseFromApi confrimCreationAccount(String token, HttpServletRequest httpRequest) throws GenericError {
		logger.info(token);
		VerificationToken verifToken = repo.findVerificationByToken(token);
		ClientBank clientBank = verifToken.getClientBank();
		String message = "";
		HttpStatus statusCode;
		String status = verifToken.getClientBank().getStatus();
		StringBuilder sb = new StringBuilder("ACCOUNT FOR THE USER: ")
				.append(verifToken.getClientBank().getFirstName().toUpperCase()).append(" ")
				.append(verifToken.getClientBank().getLastName().toUpperCase());
		switch  (status) {
		case Constants.IS_VERIFICATING:
				message = sb.append(" IS CORRECTLY VERIFICATE!").toString();
				statusCode = HttpStatus.OK;
				verifToken.getClientBank().setStatus(Constants.VERIFICATE);
				break;
		case Constants.VERIFICATE:
				message = sb.append(" IS ALREADY VERIFICATE").toString();
				statusCode = HttpStatus.OK;
				break;
		default:
			repo.delete(verifToken);
			publisher.publishEvent(new RegistrationCompleteEvent(clientBank, GenericMethod.generateUrl(httpRequest)));
			verifToken = new VerificationToken(clientBank, token);
			message = sb.append(" IS NOT VERIFICATE, GENERETING A NEW TOKEN ON YOUR EMAIL FOR CONFIRM YOUR ACCOUNT - IF THE NEW TOKEN DON'T SUCCESS, RE-CREATE YOUR ACCOUNT").toString();
			statusCode = HttpStatus.BAD_REQUEST;
			clientBank.setStatus(Constants.IS_VERIFICATING);
		}
		
		return new ResponseFromApi(message, statusCode);
	}

}
