package com.progetto.validators;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.progetto.utils.Constants;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class CredentialCustomValidator implements ConstraintValidator<CredentialCustomConstraint, String> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if (username.startsWith(Constants.ADMIN_CODE_STARTING)
				|| Pattern.compile(Constants.REGEX_EMAIL).matcher(username).matches()) {
			return true;
		}
		return false;
	}
}
