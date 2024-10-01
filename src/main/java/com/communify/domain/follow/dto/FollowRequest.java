package com.communify.domain.follow.dto;

import com.communify.domain.follow.error.exception.SelfFollowException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class FollowRequest {

    private final Long followerId;
    private final String followerName;
    private final Long followeeId;

    public FollowRequest(final Long followerId, final String followerName, final Long followeeId) {
        if (Objects.equals(followerId, followeeId)) {
            throw new SelfFollowException(followerId);
        }

        this.followerId = followerId;
        this.followerName = followerName;
        this.followeeId = followeeId;
    }
}
