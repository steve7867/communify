package com.communify.domain.certification.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class CertificationCodeNotPublishedException extends InvalidAccessException {

    public static final String MESSAGE = "인증 코드를 먼저 요청해주세요.";

    public CertificationCodeNotPublishedException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
