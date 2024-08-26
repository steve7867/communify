package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class InfoForCommentNotification extends InfoForNotification {

    private final Long postWriterId;
    private final String fcmToken;
    private final String commentContent;
    private final String commentWriterName;

    @Override
    public Boolean isPushable() {
        return isPostWriterExisting() && isFcmTokenExisting();
    }

    @Override
    PushRequest makePushRequest() {
        final MessageDto messageDto = MessageDto.forComment(commentWriterName, commentContent);
        return new PushRequest(fcmToken, messageDto);
    }

    private Boolean isPostWriterExisting() {
        return Objects.nonNull(postWriterId);
    }

    private Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }
}
