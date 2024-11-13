package com.communify.domain.comment.exception;

import com.communify.global.error.exception.InvalidAccessException;
import org.springframework.http.HttpStatus;

public class InvalidCommentAccessException extends InvalidAccessException {

    private static final String MESSAGE = "댓글 접근 권한이 없습니다.";

    public InvalidCommentAccessException() {
        super(HttpStatus.UNAUTHORIZED, MESSAGE);
    }
}
