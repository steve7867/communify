package com.communify.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentEditRequest {

    private final Long postId;
    private final Long commentId;
    private final String content;
    private final Long requesterId;
}
