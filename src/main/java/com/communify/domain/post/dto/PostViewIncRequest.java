package com.communify.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostViewIncRequest {

    private final Long postId;
    private final Integer viewCount;
}
