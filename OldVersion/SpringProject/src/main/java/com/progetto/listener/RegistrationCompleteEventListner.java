package com.progetto.listener;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.progetto.domain.ClientBank;
import com.progetto.event.RegistrationCompleteEvent;
import com.progetto.exception.GenericError;
import com.progetto.service.IverificationToken;
@Component
public class RegistrationCompleteEventListner implements ApplicationListener<RegistrationCompleteEvent>{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IverificationToken verificaitonTokenService;
	
	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		
		try {
			ClientBank client = event.getClient();
			String token = UUID.randomUUID().toString();
			verificaitonTokenService.save(client, token);
			String url = event.getApplicaitonUrl() + "verifyRegistration?token" + token;
			logger.info("Click the link to verify yout account: {}", url);
		} catch (GenericError e) {
			logger.error(e.toString());
		}
	}

}
