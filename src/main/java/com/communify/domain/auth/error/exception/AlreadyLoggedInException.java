package com.communify.domain.auth.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class AlreadyLoggedInException extends InvalidAccessException {

    private static final String MESSAGE = "이미 로그인하셨습니다.";

    public AlreadyLoggedInException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
