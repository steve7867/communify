package com.communify.domain.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(force = true)
public class LikeInfoForNotification {

    private final Long postWriterId;
    private final String fcmToken;
    private final Long likerId;
    private final String likerName;
    private final String state;

    public Boolean isPostWriterExist() {
        return Objects.nonNull(postWriterId);
    }

    public Boolean isFcmTokenExist() {
        return Objects.nonNull(fcmToken);
    }

    public Boolean isWriterSameAsLiker() {
        return Objects.equals(postWriterId, likerId);
    }

    public Boolean isFresh() {
        return Objects.equals(state, "fresh");
    }
}
