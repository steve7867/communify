package com.communify.domain.post.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HotPostCategoryEditException extends InvalidAccessException {

    public static final String MESSAGE = "인기 게시글의 카테고리는 수정할 수 없습니다.";
    private final Long postId;

    public HotPostCategoryEditException(final Long postId) {
        super(HttpStatus.BAD_REQUEST, MESSAGE);
        this.postId = postId;
    }
}
