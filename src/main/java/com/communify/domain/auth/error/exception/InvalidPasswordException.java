package com.communify.domain.auth.error.exception;

import com.communify.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends InvalidValueException {

    private final String password;

    public InvalidPasswordException(String password) {
        super("유효하지 않은 패스워드입니다.");
        this.password = password;
    }
}
