package com.communify.domain.comment.dto.event;

import com.communify.domain.comment.dto.CommentUploadRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUploadEvent {

    private final CommentUploadRequest commentUploadRequest;

    public Long getMemberId() {
        return commentUploadRequest.getMemberId();
    }

    public String getMemberName() {
        return commentUploadRequest.getMemberName();
    }

    public String getContent() {
        return commentUploadRequest.getContent();
    }

    public Long getPostId() {
        return commentUploadRequest.getPostId();
    }
}
