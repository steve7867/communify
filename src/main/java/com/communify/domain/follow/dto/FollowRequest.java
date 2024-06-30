package com.communify.domain.follow.dto;

import com.communify.domain.follow.error.exception.SelfFollowException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class FollowRequest {

    private final Long followerId;
    private final String followerName;
    private final Long followedId;

    public FollowRequest(final Long followerId, final String followerName, final Long followedId) {
        if (Objects.equals(followerId, followedId)) {
            throw new SelfFollowException(followerId);
        }

        this.followerId = followerId;
        this.followerName = followerName;
        this.followedId = followedId;
    }
}
