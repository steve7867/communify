package com.communify.domain.post.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class PostNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE_FORMAT = "%d번 게시글은 존재하지 않습니다.";
    private final Long postId;

    public PostNotFoundException(Long postId) {
        super(String.format(MESSAGE_FORMAT, postId));
        this.postId = postId;
    }
}
