package com.communify.domain.push.dto;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class PushInfoForFollow extends PushInfo {

    private final String fcmToken;

    private final String followerName;

    @Override
    public Boolean isPushable() {
        return isFcmTokenExisting();
    }

    private Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }

    @Override
    MessageDto makeMessageDto() {
        return MessageDto.forFollow(fcmToken, followerName);
    }
}
