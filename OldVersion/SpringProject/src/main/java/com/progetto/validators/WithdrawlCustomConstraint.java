package com.progetto.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WithdrawlCustomValidator.class)
public @interface WithdrawlCustomConstraint {

	String message() default "CIFRA INSERITA NON VALIDA, NON HAI ABBASTANZA FONDI";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String iban();
	String amount();
	String operation();

}