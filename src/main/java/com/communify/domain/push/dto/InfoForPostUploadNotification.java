package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class InfoForPostUploadNotification extends InfoForNotification {

    private final Long followerId;
    private final String fcmToken;
    private final String writerName;

    @Override
    public Boolean isPushable() {
        return isFcmTokenExisting();
    }

    @Override
    PushRequest makePushRequest() {
        final MessageDto messageDto = MessageDto.forPostUpload(writerName);
        return new PushRequest(fcmToken, messageDto);
    }

    private Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }
}
