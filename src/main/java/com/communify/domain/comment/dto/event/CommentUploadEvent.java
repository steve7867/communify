package com.communify.domain.comment.dto.event;

import com.communify.domain.comment.dto.CommentUploadRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentUploadEvent {

    private final CommentUploadRequest request;

    public Long getPostId() {
        return request.getPostId();
    }

    public String getCommentContent() {
        return request.getCommentContent();
    }

    public Long getCommentWriterId() {
        return request.getCommentWriterId();
    }

    public String getCommentWriterName() {
        return request.getCommentWriterName();
    }
}
