package com.progetto.utils;

import java.math.BigInteger;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class IbanGenerator {

	public static String generateIban() {
		
		String accountNumber = createAccountNumber();
		String bankCode = ParamReader.getParam(Constants.BANK_CODE);
		String countryCode = ParamReader.getParam(Constants.COUNTRY_CODE);
		String cab = ParamReader.getParam(Constants.CAB);
		String controlDigit = ParamReader.getParam(Constants.CONTROL_DIGIT);
		String controlNationalCode = ParamReader.getParam(Constants.NATIONAL_CODE);
		String checkDigitStr = "";
		
		StringBuilder sb = new StringBuilder(bankCode).append(cab).append(accountNumber).append(controlDigit)
				.append("00").append("2521").append("00");
		int checkDigit = 98 - new BigInteger(sb.toString()).mod(new BigInteger("97")).intValue();
		checkDigitStr = String.valueOf(checkDigit);
		
		if(checkDigit < 10) {
			checkDigitStr = new StringBuilder("0").append(checkDigit).toString();
		}
		return new StringBuilder(countryCode).append(checkDigitStr).append(controlNationalCode).append(bankCode)
				.append(cab).append(accountNumber).toString();

	}

	private static String createAccountNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	
}
