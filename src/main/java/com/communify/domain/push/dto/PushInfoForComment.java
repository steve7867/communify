package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class PushInfoForComment extends PushInfo {

    private final String fcmToken;

    private final String commentContent;
    private final String commentWriterName;

    @Override
    public Boolean isPushable() {
        return isFcmTokenExisting();
    }

    private Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }

    @Override
    MessageDto makeMessageDto() {
        return MessageDto.forComment(fcmToken, commentWriterName, commentContent);
    }
}
