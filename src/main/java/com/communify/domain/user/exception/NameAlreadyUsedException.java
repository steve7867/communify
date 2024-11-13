package com.communify.domain.user.exception;

import com.communify.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class NameAlreadyUsedException extends InvalidValueException {

    private static final String message = "%s는 이미 사용 중인 이름입니다.";
    private final String name;

    public NameAlreadyUsedException(String name, Throwable cause) {
        super(String.format(message, name), cause);
        this.name = name;
    }
}
