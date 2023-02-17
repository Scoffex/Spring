package com.progetto.validators;

import java.util.NoSuchElementException;
import java.util.Optional;

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
			Optional<ClientBank> client = repo.findAll().stream().filter(x -> x.getEmail().equals(email)).findFirst();
			client.get();
			return false;
		} catch (NoSuchElementException e) {
			return true;
		}

	}
}
