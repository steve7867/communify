package com.communify.domain.post.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class PostNotFoundException extends EntityNotFoundException {

    private final Long postId;

    public PostNotFoundException(Long postId) {
        super(String.format("%d번 게시글은 존재하지 않습니다.", postId));
        this.postId = postId;
    }
}
