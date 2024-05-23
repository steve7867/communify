package com.communify.domain.Auth.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class NotLoggedInException extends InvalidAccessException {

    private static final String message = "로그인되어 있지 않습니다.";

    public NotLoggedInException() {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
