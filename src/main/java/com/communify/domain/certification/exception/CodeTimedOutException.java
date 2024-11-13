package com.communify.domain.certification.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class CodeTimedOutException extends InvalidAccessException {

    public static final String MESSAGE = "인증 시간을 초과했습니다.";

    public CodeTimedOutException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
