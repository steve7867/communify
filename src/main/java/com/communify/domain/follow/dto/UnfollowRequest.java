package com.communify.domain.follow.dto;

import com.communify.domain.follow.error.exception.SelfUnfollowException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UnfollowRequest {

    private final Long memberId;
    private final Long followId;

    public UnfollowRequest(final Long memberId, final Long followId) {
        if (Objects.equals(memberId, followId)) {
            throw new SelfUnfollowException(memberId);
        }

        this.memberId = memberId;
        this.followId = followId;
    }
}
