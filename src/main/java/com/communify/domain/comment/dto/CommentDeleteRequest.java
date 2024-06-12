package com.communify.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDeleteRequest {

    private final Long postId;
    private final Long commentId;
    private final Long memberId;
}
