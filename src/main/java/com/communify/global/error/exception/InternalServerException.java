package com.communify.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerException extends BusinessException {

    private static final String MESSAGE = "서버 내부의 오류입니다.";

    public InternalServerException(final String message, final Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }
}
