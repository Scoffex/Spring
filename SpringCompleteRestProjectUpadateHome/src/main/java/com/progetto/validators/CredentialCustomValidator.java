package com.progetto.validators;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.progetto.utils.Constants;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class CredentialCustomValidator implements ConstraintValidator<CredentialCustomConstraint, String> {


	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if (username.startsWith(Constants.ADMIN_CODE_STARTING)
				|| Pattern.compile(Constants.REGEX_EMAIL).matcher(username).matches()) {
			return true;
		}
		return false;
	}
}
  