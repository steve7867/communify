package com.communify.domain.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowEvent {

    private final FollowRequest followRequest;

    public String getMemberName() {
        return followRequest.getMemberName();
    }

    public Long getFollowId() {
        return followRequest.getFollowId();
    }
}
