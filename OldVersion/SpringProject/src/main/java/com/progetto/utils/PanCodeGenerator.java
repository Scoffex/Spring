package com.progetto.utils;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.progetto.domain.ClientBank;
import com.progetto.repository.ClientBankRepo;

@Component
public class PanCodeGenerator {

	@Autowired
	private ClientBankRepo clientRepo;

	
	
	public String panCodeGenerator() {
		String panCode = panCodeCreation();
		List<ClientBank> user = clientRepo.findAll();
		for (ClientBank userSt : user) {
			if (userSt.getPanCode().equals(Crypthography.encrypt(panCode))) {
				panCodeGenerator();
			}
		}
		return panCode;
	}

	private static String panCodeCreation() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
}
