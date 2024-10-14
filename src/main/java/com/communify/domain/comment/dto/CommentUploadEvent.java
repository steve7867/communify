package com.communify.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUploadEvent {

    private final Long postId;
    private final String content;
    private final String writerName;
}
