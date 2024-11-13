package com.communify.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserInfoForSearch {

    private final Long id;
    private final String email;
    private final String name;
    private final Integer followerCount;
    private final Integer followeeCount;
    private final Boolean isFollowing;
    private final LocalDateTime createdDateTime;
}
