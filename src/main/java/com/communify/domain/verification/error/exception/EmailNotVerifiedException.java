package com.communify.domain.verification.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmailNotVerifiedException extends InvalidAccessException {

    private static final String MESSAGE_FORMAT = "%s은 인증되지 않은 이메일입니다. 먼저 인증을 진행해주세요.";
    private final String email;

    public EmailNotVerifiedException(final String email) {
        super(HttpStatus.BAD_REQUEST, String.format(MESSAGE_FORMAT, email));
        this.email = email;
    }
}
