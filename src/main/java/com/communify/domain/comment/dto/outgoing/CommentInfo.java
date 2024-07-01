package com.communify.domain.comment.dto.outgoing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CommentInfo {

    private final Long id;
    private final String content;
    private final Long writerId;
    private final String writerName;
    private final LocalDateTime createdDateTime;
}
