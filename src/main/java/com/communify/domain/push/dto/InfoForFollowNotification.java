package com.communify.domain.push.dto;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class InfoForFollowNotification extends InfoForNotification {

    private final Long followeeId;
    private final String fcmToken;
    private final String followerName;

    @Override
    public Boolean isPushable() {
        return isFolloweeExisting() && isFcmTokenExisting();
    }

    @Override
    PushRequest makePushRequest() {
        final MessageDto messageDto = MessageDto.forFollow(followerName);
        return new PushRequest(fcmToken, messageDto);
    }

    private Boolean isFolloweeExisting() {
        return Objects.nonNull(followeeId);
    }

    private Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }
}
