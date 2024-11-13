package com.communify.domain.post.exception;

import com.communify.global.error.exception.InvalidAccessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidPostAccessException extends InvalidAccessException {

    public static final String MESSAGE = "게시글에 접근할 권한이 없습니다.";
    private final Long postId;
    private final Long userId;

    public InvalidPostAccessException(Long postId, Long userId) {
        super(HttpStatus.UNAUTHORIZED, MESSAGE);
        this.postId = postId;
        this.userId = userId;
    }
}
