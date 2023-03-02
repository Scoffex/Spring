package com.progetto.validators;

import org.springframework.beans.factory.annotation.Autowired;

import com.progetto.domain.BankAccount;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.utils.Crypthography;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IbanCustomValidator implements ConstraintValidator<IbanCustomConstraint, String> {

	@Autowired
	ClientAccountBankRepo repo;

	@Override
	public boolean isValid(String iban, ConstraintValidatorContext context) {
		BankAccount cb = repo.findByIban(Crypthography.encrypt(iban));
		return cb == null ? false : true;

	}

}