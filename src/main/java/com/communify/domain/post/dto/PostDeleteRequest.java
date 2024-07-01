package com.communify.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostDeleteRequest {

    private final Long postId;
    private final Long requesterId;
}
