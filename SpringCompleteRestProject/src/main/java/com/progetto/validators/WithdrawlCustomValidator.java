package com.progetto.validators;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.progetto.domain.BankAccount;
import com.progetto.repository.ClientAccountBankRepo;
import com.progetto.utils.Constants;
import com.progetto.utils.Crypthography;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WithdrawlCustomValidator implements ConstraintValidator<WithdrawlCustomConstraint, Object> {

	@Autowired
	ClientAccountBankRepo repo;


	String operation;
	String amount;
	String iban;
	String message;

	public void initialize(WithdrawlCustomConstraint ca) {
		this.operation = ca.operation();
		this.amount = ca.amount();
		this.iban = ca.iban();
		this.message = ca.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);

		String operationValid = (String) beanWrapper.getPropertyValue(operation);

		String ibanValid = (String) beanWrapper.getPropertyValue(iban);

		double amountValid = (Double) beanWrapper.getPropertyValue(amount);

		boolean valid = true;
		BankAccount cb = repo.findByIban(Crypthography.encrypt(ibanValid));
		if (cb != null && Constants.WITHDRAWL.equalsIgnoreCase(operationValid)) {
			valid = amountValid <= cb.getAmount() ? true : false;
		} 

		return valid;

	}

}