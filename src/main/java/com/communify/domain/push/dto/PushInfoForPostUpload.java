package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class PushInfoForPostUpload extends PushInfo {

    private final String fcmToken;

    private final String writerName;

    @Override
    public Boolean isPushable() {
        return isFcmTokenExisting();
    }

    private Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }

    @Override
    MessageDto makeMessageDto() {
        return MessageDto.forPostUpload(fcmToken, writerName);
    }
}
