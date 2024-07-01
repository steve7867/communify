package com.communify.domain.comment.dto.event;

import com.communify.domain.comment.dto.CommentUploadRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUploadEvent {

    private final CommentUploadRequest commentUploadRequest;
}
