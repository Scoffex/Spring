package com.progetto.utils;

import java.util.List;

import com.progetto.dto.BankAccountClientDto;
import com.progetto.dto.ResponseFromApi;

public class DecryptorData {

	private DecryptorData() {
	}

	public static ResponseFromApi decryptIban(ResponseFromApi response) {
		if (response.getBankAccount() == null) {
			String iban = Crypthography.decrypt(response.getBankAccountSingle().getIban());
			response.getBankAccountSingle().setIban(iban);
		} else {
			List<BankAccountClientDto> list = response.getBankAccount();
			response.getBankAccount().forEach(x -> x.setIban(Crypthography.decrypt(x.getIban())));
			response.setBankAccount(list);
		}
		return response;
	}

	public static ResponseFromApi decryptPanCode(ResponseFromApi response) {
		response.getSingleClient().setPanCode(Crypthography.decrypt(response.getSingleClient().getPanCode()));
		return response;
	}

	public static ResponseFromApi decryptPanCodeIban(ResponseFromApi response) {
		decryptIban(response);
		decryptPanCode(response);
		return response;
	}

}
