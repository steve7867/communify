package com.communify.domain.user.exception;

import com.communify.global.error.exception.EntityNotFoundException;

public class TokenNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = "토큰이 존재하지 않습니다.";

    public TokenNotFoundException() {
        super(MESSAGE);
    }
}
