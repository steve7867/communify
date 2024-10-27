package com.communify.domain.post.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class AlreadyLikedException extends InvalidAccessException {

    private static final String MESSAGE = "좋아요는 하루에 한번만 가능합니다.";

    public AlreadyLikedException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
