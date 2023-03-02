package com.progetto.service;

public interface IemailSenderService {

	public void sendSimpleEmail(String toEmail, String body, String subject);
}
