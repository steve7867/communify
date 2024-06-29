package com.communify.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class PostWriterInfoForNotification {

    private final Long writerId;
    private final String fcmToken;

    public Boolean isFcmTokenExisting() {
        return Objects.nonNull(fcmToken);
    }
}
