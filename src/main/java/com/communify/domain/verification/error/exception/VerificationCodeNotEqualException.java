package com.communify.domain.verification.error.exception;

import com.communify.global.error.exception.InvalidValueException;

public class VerificationCodeNotEqualException extends InvalidValueException {

    private static final String MESSAGE = "인증 코드가 일치하지 않습니다.";

    public VerificationCodeNotEqualException() {
        super(MESSAGE);
    }
}
