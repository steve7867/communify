package com.communify.domain.verification.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class VerificationTimeOutException extends InvalidAccessException {

    public VerificationTimeOutException() {
        super(HttpStatus.BAD_REQUEST, "인증 시간을 초과했습니다.");
    }
}
