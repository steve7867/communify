package com.communify.domain.push.dto;

public abstract class InfoForNotification implements PushableEvaluator {

    public final PushRequest generatePushRequest() {
        if (!isPushable()) {
            throw new IllegalStateException("푸쉬 알림을 보낼 수 없습니다.");
        }

        return makePushRequest();
    }

    abstract PushRequest makePushRequest();
}
