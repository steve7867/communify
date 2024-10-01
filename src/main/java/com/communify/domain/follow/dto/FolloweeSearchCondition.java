package com.communify.domain.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FolloweeSearchCondition {

    private final Long followerId;
    private final Long lastFolloweeId;
    private final Long searcherId;
    private final Integer limit = 100;
}
