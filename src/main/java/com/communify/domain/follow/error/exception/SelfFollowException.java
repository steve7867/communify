package com.communify.domain.follow.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SelfFollowException extends InvalidAccessException {

    private static final String message = "자기 자신을 팔로우 할 수 없습니다.";
    private final Long memberId;

    public SelfFollowException(Long memberId) {
        super(HttpStatus.BAD_REQUEST, message);
        this.memberId = memberId;
    }
}
