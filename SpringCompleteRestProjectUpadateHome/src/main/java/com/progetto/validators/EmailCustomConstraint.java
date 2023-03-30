package com.progetto.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.progetto.utils.Constants;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

	@Documented
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = EmailCustomValidator.class)
	public @interface EmailCustomConstraint {
		String message() default Constants.ERROR_SAVE_EMAIL_UNIQUE;
		Class<?>[] groups() default {};
		Class<? extends Payload>[] payload() default {};
		

	
}
