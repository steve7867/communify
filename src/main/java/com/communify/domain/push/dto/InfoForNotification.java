package com.communify.domain.push.dto;

public interface InfoForNotification {

    Boolean canSendNotification();

    PushRequest generatePushRequest();
}
