package com.communify.domain.push.dto;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class InfoForFollowNotification implements InfoForNotification {

    private final Long followedId;
    private final String fcmToken;
    private final String followerName;

    @Override
    public Boolean isPushable() {
        return isFollowedExisting() && isFcmTokenExisting();
    }

    @Override
    public PushRequest generatePushRequest() {
        final MessageDto messageDto = MessageDto.forFollow(followerName);
        return new PushRequest(fcmToken, messageDto);
    }

    private Boolean isFollowedExisting() {
        return Objects.nonNull(followedId);
    }

    private Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }
}
