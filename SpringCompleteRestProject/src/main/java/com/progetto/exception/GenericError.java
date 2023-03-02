package com.progetto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GenericError extends Exception{
    public GenericError(String message) {
        super(message);
    }

}
