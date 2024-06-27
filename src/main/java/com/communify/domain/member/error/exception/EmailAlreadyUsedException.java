package com.communify.domain.member.error.exception;

import com.communify.global.error.exception.InvalidValueException;
import lombok.Getter;

@Getter
public class EmailAlreadyUsedException extends InvalidValueException {

    private static final String MESSAGE_FORMAT = "%s은 이미 사용 중인 이메일입니다.";
    private final String email;

    public EmailAlreadyUsedException(final String email) {
        super(String.format(MESSAGE_FORMAT, email));
        this.email = email;
    }

    public EmailAlreadyUsedException(final String email, final Throwable cause) {
        super(String.format(MESSAGE_FORMAT, email), cause);
        this.email = email;
    }
}
