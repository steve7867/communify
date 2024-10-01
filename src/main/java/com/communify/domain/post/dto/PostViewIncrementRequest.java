package com.communify.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostViewIncrementRequest {

    private final Long postId;
    private final Integer viewCount;
}
