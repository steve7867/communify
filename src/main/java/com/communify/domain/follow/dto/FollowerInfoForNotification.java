package com.communify.domain.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class FollowerInfoForNotification {

    private final String followerId;
    private final String fcmToken;

    public Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }
}
