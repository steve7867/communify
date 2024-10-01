package com.communify.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(final HttpStatus status, final String message, final Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
