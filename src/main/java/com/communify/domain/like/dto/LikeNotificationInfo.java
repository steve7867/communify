package com.communify.domain.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeNotificationInfo {

    private final Long postId;
    private final String fcmToken;
    private final Long likerId;
    private final String likerName;
}
