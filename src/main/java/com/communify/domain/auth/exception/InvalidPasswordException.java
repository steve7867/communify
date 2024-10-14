package com.communify.domain.auth.exception;

import com.communify.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends InvalidValueException {

    public static final String MESSAGE = "유효하지 않은 패스워드입니다.";
    private final String password;

    public InvalidPasswordException(String password) {
        super(MESSAGE);
        this.password = password;
    }
}
