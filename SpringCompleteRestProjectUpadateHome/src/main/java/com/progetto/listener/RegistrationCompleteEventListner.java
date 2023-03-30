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
import com.progetto.service_impl.EmailSenderServiceImpl;
@Component
public class RegistrationCompleteEventListner implements ApplicationListener<RegistrationCompleteEvent>{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IverificationToken verificaitonTokenService;
	
	@Autowired
	EmailSenderServiceImpl emailSender;
	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		
		try {
			ClientBank client = event.getClient();
			String token = UUID.randomUUID().toString();
			verificaitonTokenService.save(client, token);
			String url = new StringBuilder(event.getApplicaitonUrl()).append("/verifyRegistration/").append(token).toString();
			String message = new StringBuilder("Click the link to verify yout account: ").append(url).toString();
			logger.info(message);
			emailSender.sendSimpleEmail(client.getEmail(), message, "Prova Email"); 
		} catch (GenericError e) {
			logger.error(e.toString());
		}
	}

}
