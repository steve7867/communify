package com.communify.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentInfo {

    private final Long id;
    private final String content;
    private final Long memberId;
    private final String memberName;
    private final LocalDateTime createdDateTime;
}
