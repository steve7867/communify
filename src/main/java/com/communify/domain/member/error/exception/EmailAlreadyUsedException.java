package com.communify.domain.member.error.exception;

import com.communify.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class EmailAlreadyUsedException extends InvalidValueException {

    private final String email;

    public EmailAlreadyUsedException(String email, Throwable cause) {
        super(String.format("%s은 이미 사용 중인 이메일입니다.", email), cause);
        this.email = email;
    }
}
