package com.progetto.validators;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.progetto.domain.ClientBank;
import com.progetto.repository.ClientBankRepo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class EmailCustomValidator implements ConstraintValidator<EmailCustomConstraint, String> {

	@Autowired
	ClientBankRepo repo;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		try {
			for (ClientBank client : repo.findAll()) {
				if (client.getEmail().equals(email)) {
					return false;
				}
			}

		} catch (NoSuchElementException e) {
			return true;
		}
		return true;

	}
}
