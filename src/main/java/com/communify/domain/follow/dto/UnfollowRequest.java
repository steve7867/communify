package com.communify.domain.follow.dto;

import com.communify.domain.follow.error.exception.SelfUnfollowException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UnfollowRequest {

    private final Long followerId;
    private final Long followeeId;

    public UnfollowRequest(final Long followerId, final Long followeeId) {
        if (Objects.equals(followerId, followeeId)) {
            throw new SelfUnfollowException(followerId);
        }

        this.followerId = followerId;
        this.followeeId = followeeId;
    }
}
