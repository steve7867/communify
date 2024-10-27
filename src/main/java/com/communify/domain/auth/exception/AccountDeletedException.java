package com.communify.domain.auth.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class AccountDeletedException extends InvalidAccessException {

    private static final String message = "계정이 삭제되어 로그인 할 수 없습니다.";

    public AccountDeletedException() {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
