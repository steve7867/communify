package com.communify.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
