package com.progetto.conversion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.progetto.domain.BankAccount;
import com.progetto.domain.ClientBank;
import com.progetto.dto.BankAccountClientDto;
import com.progetto.dto.ClientBankDto;
import com.progetto.dto.PanCodeInfo;
import com.progetto.dto.ResponseFromApi;

public class ConversionAnagraficAccount {

	private ConversionAnagraficAccount() {
	}

//CLIENTBANK	
	public static ClientBank fromDtoToDomain(ClientBankDto usdto) {
		ClientBank user = new ClientBank();
		user.setBorningDate(usdto.getBirth());
		user.setEmail(usdto.getEmail());
		user.setFirstName(usdto.getName());
		user.setLastName(usdto.getSurname());
		user.setPassword(usdto.getPassword());
		return user;
	}

	public static ClientBankDto fromDomainToDto(ClientBank user) {
		ClientBankDto usdto = new ClientBankDto();
		usdto.setBirth(user.getBorningDate());
		usdto.setEmail(user.getEmail());
		usdto.setName(user.getFirstName());
		usdto.setPassword(user.getPassword());
		usdto.setSurname(user.getLastName());
		return usdto;
	}

	// BANKACCOUNT

	public static BankAccountClientDto fromDomainBankToDto(BankAccount ba) {
		BankAccountClientDto dtoBank = new BankAccountClientDto();
		dtoBank.setAmount(ba.getAmount());
		dtoBank.setCreationDate(ba.getDateCreationAccount());
		dtoBank.setCurrency(ba.getCurrency());
		dtoBank.setIban(ba.getIban());
		dtoBank.setPropetary(new StringBuilder(ba.getClientBank().getFirstName()).append(" ")
				.append(ba.getClientBank().getLastName()).toString());
		return dtoBank;
	}

	public static BankAccount fromDtoBankToDomain(BankAccountClientDto dto) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAmount(dto.getAmount());
		bankAccount.setCurrency(dto.getCurrency());
		bankAccount.setDateCreationAccount(dto.getCreationDate());
		bankAccount.setIban(dto.getIban());
		return bankAccount;
	}

	public static List<BankAccountClientDto> fromListDomainBanotoListDtoBank(List<BankAccount> bankList) {
		List<BankAccountClientDto> list = new ArrayList<>();
		for (BankAccount bank : bankList) {
			list.add(fromDomainBankToDto(bank));
		}
		return list;
	}

	public static ResponseFromApi fromDomainToResponse(ClientBank clientBank, List<BankAccount> ba, String message,
			HttpStatus statuCode) {
		ResponseFromApi response = new ResponseFromApi();
		response.setSingleClient(clientBank);
		if (ba != null)
			response.setBankAccount(fromListDomainBanotoListDtoBank(ba));
		response.setStatudCode(statuCode);
		response.setMessage(message);
		return response;
	}

}
