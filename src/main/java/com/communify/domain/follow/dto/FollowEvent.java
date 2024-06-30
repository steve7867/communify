package com.communify.domain.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowEvent {

    private final FollowRequest followRequest;

    public String getFollowerName() {
        return followRequest.getFollowerName();
    }

    public Long getFollowId() {
        return followRequest.getFollowedId();
    }
}
