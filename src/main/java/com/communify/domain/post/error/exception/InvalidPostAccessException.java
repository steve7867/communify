package com.communify.domain.post.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidPostAccessException extends InvalidAccessException {

    private final Long postId;
    private final Long memberId;

    public InvalidPostAccessException(Long postId, Long memberId) {
        super(HttpStatus.UNAUTHORIZED, "게시글에 접근할 권한이 없습니다.");
        this.postId = postId;
        this.memberId = memberId;
    }
}
