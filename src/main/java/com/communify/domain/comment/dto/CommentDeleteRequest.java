package com.communify.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentDeleteRequest {

    private final Long postId;
    private final Long commentId;
    private final Long memberId;
}
