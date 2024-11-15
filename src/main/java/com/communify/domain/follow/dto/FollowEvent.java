package com.communify.domain.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowEvent {

    private final Long followeeId;
    private final String followerName;
}
