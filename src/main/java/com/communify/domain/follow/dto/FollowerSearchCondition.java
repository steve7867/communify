package com.communify.domain.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowerSearchCondition {

    private final Long followedId;
    private final Long lastFollowerId;
    private final Long searcherId;
    private final Integer limit = 100;
}
