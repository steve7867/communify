package com.communify.domain.member.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class MemberInfo {

    private final Long id;
    private final String email;
    @JsonIgnore
    private final String hashed;
    private final String name;
    private final Integer followerCount;
    private final Integer followeeCount;
    private final LocalDateTime createdDateTime;

    private final Boolean isFollowing;

    @JsonIgnore
    private final String fcmToken;
}
