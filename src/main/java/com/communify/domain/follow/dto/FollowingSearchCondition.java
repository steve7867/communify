package com.communify.domain.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowingSearchCondition {

    private final Long followerId;
    private final Long lastFollowingId;
    private final Long searcherId;
    private final Integer limit = 100;
}
