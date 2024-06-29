package com.communify.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUploadRequest {

    private final Long postId;
    private final String commentContent;
    private final Long commentWriterId;
    private final String commentWriterName;
}
