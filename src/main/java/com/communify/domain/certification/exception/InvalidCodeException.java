package com.communify.domain.certification.exception;

import com.communify.global.error.exception.InvalidValueException;

public class InvalidCodeException extends InvalidValueException {

    private static final String MESSAGE = "인증 코드가 일치하지 않습니다.";

    public InvalidCodeException() {
        super(MESSAGE);
    }
}
