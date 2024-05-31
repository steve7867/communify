package com.communify.domain.member.error.exception;

import com.communify.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class EmailAlreadyUsedException extends InvalidValueException {

    private static final String errorMessage = "%s은 이미 사용 중인 이메일입니다.";
    private final String email;

    public EmailAlreadyUsedException(String email) {
        super(String.format(errorMessage, email));
        this.email = email;
    }

    public EmailAlreadyUsedException(String email, Throwable cause) {
        super(String.format(errorMessage, email), cause);
        this.email = email;
    }
}
