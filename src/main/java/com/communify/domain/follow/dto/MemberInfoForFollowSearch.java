package com.communify.domain.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberInfoForFollowSearch {

    private final Long id;
    private final String name;
    private final Boolean isFollowing;
}
