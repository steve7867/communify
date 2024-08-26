package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(force = true)
public class InfoForLikeNotification extends InfoForNotification {

    private final Long postWriterId;
    private final String fcmToken;
    private final Long likerId;
    private final String likerName;
    private final String state;

    @Override
    public Boolean isPushable() {
        return isPostWriterExist()
                && isFcmTokenExist()
                && !isPostWriterEqualToLiker()
                && isFresh();
    }

    @Override
    PushRequest makePushRequest() {
        final MessageDto messageDto = MessageDto.forPostLike(likerName);
        return new PushRequest(fcmToken, messageDto);
    }

    private Boolean isPostWriterExist() {
        return Objects.nonNull(postWriterId);
    }

    private Boolean isFcmTokenExist() {
        return Objects.nonNull(fcmToken);
    }

    private Boolean isPostWriterEqualToLiker() {
        return Objects.equals(postWriterId, likerId);
    }

    private Boolean isFresh() {
        return Objects.equals(state, "fresh");
    }
}
