package com.communify.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidAccessException extends BusinessException {

    public InvalidAccessException(HttpStatus status, String message) {
        super(status, message);
    }
}
