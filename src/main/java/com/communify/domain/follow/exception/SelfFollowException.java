package com.communify.domain.follow.exception;

import com.communify.global.error.exception.InvalidAccessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SelfFollowException extends InvalidAccessException {

    private static final String MESSAGE = "자기 자신을 팔로우 할 수 없습니다.";

    public SelfFollowException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
