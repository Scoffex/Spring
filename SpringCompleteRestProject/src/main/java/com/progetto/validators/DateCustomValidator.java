package com.progetto.validators;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateCustomValidator implements ConstraintValidator<DateCustomConstraint, LocalDate> {

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
		boolean valid = false;
		if (date != null) {
			long days = ChronoUnit.DAYS.between(date, LocalDate.now());
			if (days >= 6574) {
				valid = true;
			}
		}
		return valid;
	}

}