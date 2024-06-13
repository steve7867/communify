package com.communify.domain.post.error.exception;

import com.communify.global.error.exception.InvalidAccessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HotPostCategoryEditException extends InvalidAccessException {

    private final Long postId;

    public HotPostCategoryEditException(Long postId) {
        super(HttpStatus.BAD_REQUEST, "인기 게시글의 카테고리는 수정할 수 없습니다.");
        this.postId = postId;
    }
}
