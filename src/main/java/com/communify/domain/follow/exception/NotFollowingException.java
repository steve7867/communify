package com.communify.domain.follow.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class NotFollowingException extends InvalidAccessException {

    private static final String MESSAGE = "팔로우하고 있지 않습니다.";

    public NotFollowingException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
