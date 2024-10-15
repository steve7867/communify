package com.communify.domain.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeEvent {

    private final Long postId;
    private final Long likerId;
    private final String likerName;
}
