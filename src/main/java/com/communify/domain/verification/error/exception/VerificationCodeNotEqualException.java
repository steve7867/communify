package com.communify.domain.verification.error.exception;

import com.communify.global.error.exception.InvalidValueException;

public class VerificationCodeNotEqualException extends InvalidValueException {

    public VerificationCodeNotEqualException() {
        super("인증 코드가 일치하지 않습니다.");
    }
}
