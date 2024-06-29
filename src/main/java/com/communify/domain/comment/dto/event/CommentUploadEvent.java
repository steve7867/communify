package com.communify.domain.comment.dto.event;

import com.communify.domain.comment.dto.CommentUploadRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUploadEvent {

    private final CommentUploadRequest commentUploadRequest;

    public Long getCommentWriterId() {
        return commentUploadRequest.getMemberId();
    }

    public String getCommentWriterName() {
        return commentUploadRequest.getMemberName();
    }

    public String getCommentContent() {
        return commentUploadRequest.getContent();
    }

    public Long getPostId() {
        return commentUploadRequest.getPostId();
    }
}
