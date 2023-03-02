package com.progetto.service_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.progetto.utils.Constants;

@Service
public class EmailSenderServiceImpl {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JavaMailSender emailSender;

	public void sendSimpleEmail(String toEmail, String body, String subject) {
		try {
			logger.info("SIAMO DENTRO");
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(Constants.EMAIL_SENDER);
			message.setTo(toEmail);
			message.setText(body);
			message.setSubject(subject);

			logger.info("PRIMA DEL SEND");
			emailSender.send(message);
			logger.info("DOPO DEL SEND");
		} catch (MailException e) {
			logger.error("ECCO L'ERRORE: {}", e);
		}
	}

	
}
