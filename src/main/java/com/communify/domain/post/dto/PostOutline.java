package com.communify.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class PostOutline {

    private final Long id;
    private final String title;
    private final Long writerId;
    private final String writerName;
    private final Long categoryId;
    private final Integer viewCount;
    private final Integer likeCount;
    private final Integer commentCount;
    private final Boolean isHot;
    private final LocalDateTime createdDateTime;
}
