package com.ehayes.ppmtool.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SameUsernameException extends RuntimeException {

    public SameUsernameException(String message) {
        super(message);
    }
}
