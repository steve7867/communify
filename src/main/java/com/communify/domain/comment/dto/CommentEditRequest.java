package com.communify.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentEditRequest {

    private final Long postId;
    private final Long commentId;
    private final String content;
    private final Long memberId;
}
