package com.progetto.utils;

import com.progetto.dto.BankAccountClientDto;
import com.progetto.dto.ResponseFromApi;

public class DecryptorData {

	private DecryptorData() {
	}

	public static ResponseFromApi decryptIban(ResponseFromApi response) {
		if (response.getBankAccount() == null) {
			response.getBankAccountSingle().setIban(Crypthography.decrypt(response.getBankAccountSingle().getIban()));
		} else {
			for (BankAccountClientDto bankDto : response.getBankAccount()) {
				response.getBankAccount().get(response.getBankAccount().indexOf(bankDto))
						.setIban(Crypthography.decrypt(bankDto.getIban()));
			}
//			response.setBankAccount(response.getBankAccount().stream()
//					.peek(x -> x.setIban(Crypthography.decrypt(x.getIban()))).collect(Collectors.toList()));

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
