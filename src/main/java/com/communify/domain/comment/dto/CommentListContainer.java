package com.communify.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class CommentListContainer {

    private final LocalDateTime createdDateTime;
    private final List<CommentInfo> commentInfoList;
}
