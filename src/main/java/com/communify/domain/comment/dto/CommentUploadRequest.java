package com.communify.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUploadRequest {

    private final String content;
    private final Long postId;
    private final Long memberId;
    private final String memberName;
}
