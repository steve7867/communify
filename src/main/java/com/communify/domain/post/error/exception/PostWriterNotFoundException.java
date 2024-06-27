package com.communify.domain.post.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class PostWriterNotFoundException extends EntityNotFoundException {

    public static final String MESSAGE_FORMAT = "%d번 게시글의 작성자가 존재하지 않습니다.";
    private final Long postId;

    public PostWriterNotFoundException(final Long postId) {
        super(String.format(MESSAGE_FORMAT, postId));
        this.postId = postId;
    }
}
