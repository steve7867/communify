package com.communify.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidValueException extends BusinessException {

    public InvalidValueException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public InvalidValueException(final String message, final Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }
}
