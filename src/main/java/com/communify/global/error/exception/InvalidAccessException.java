package com.communify.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidAccessException extends BusinessException {

    public InvalidAccessException(final HttpStatus status, final String message) {
        super(status, message);
    }
}
