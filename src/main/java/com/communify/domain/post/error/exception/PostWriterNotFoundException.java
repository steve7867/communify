package com.communify.domain.post.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class PostWriterNotFoundException extends EntityNotFoundException {

    private final Long postId;

    public PostWriterNotFoundException(Long postId) {
        super(String.format("%d번 게시글의 작성자가 존재하지 않습니다.", postId));
        this.postId = postId;
    }
}
