package com.communify.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentAddEvent {

    private final Long postId;
}
