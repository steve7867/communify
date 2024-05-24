package com.communify.domain.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowRequest {

    private final Long memberId;
    private final String memberName;
    private final Long followId;
}
