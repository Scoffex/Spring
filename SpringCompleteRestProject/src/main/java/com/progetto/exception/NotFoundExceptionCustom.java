package com.progetto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundExceptionCustom extends EntityNotFoundException {


	public NotFoundExceptionCustom(String message) {
		super(message);
	}

}
