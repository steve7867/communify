package com.communify.domain.verification.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class VerificationCodeNotPublishedException extends InvalidAccessException {

    public static final String MESSAGE = "인증 코드를 먼저 요청해주세요.";

    public VerificationCodeNotPublishedException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
