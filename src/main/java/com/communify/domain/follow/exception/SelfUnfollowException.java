package com.communify.domain.follow.exception;

import com.communify.global.error.exception.InvalidAccessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SelfUnfollowException extends InvalidAccessException {

    private static final String MESSAGE = "자기 자신을 언팔로우 할 수 없습니다.";

    public SelfUnfollowException() {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
