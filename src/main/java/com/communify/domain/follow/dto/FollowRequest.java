package com.communify.domain.follow.dto;

import com.communify.domain.follow.error.exception.SelfFollowException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class FollowRequest {

    private final Long memberId;
    private final String memberName;
    private final Long followId;

    public FollowRequest(final Long memberId, final String memberName, final Long followId) {
        if (Objects.equals(memberId, followId)) {
            throw new SelfFollowException(memberId);
        }

        this.memberId = memberId;
        this.memberName = memberName;
        this.followId = followId;
    }
}
