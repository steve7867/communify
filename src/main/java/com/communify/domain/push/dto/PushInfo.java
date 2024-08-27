package com.communify.domain.push.dto;

public abstract class PushInfo implements PushableEvaluator {

    public final MessageDto getMessageDto() {
        if (!isPushable()) {
            throw new IllegalStateException("푸쉬 알림을 보낼 수 없습니다.");
        }

        return makeMessageDto();
    }

    abstract MessageDto makeMessageDto();
}
