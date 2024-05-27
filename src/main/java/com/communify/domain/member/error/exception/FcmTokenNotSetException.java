package com.communify.domain.member.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class FcmTokenNotSetException extends EntityNotFoundException {

    private final Long memberId;

    public FcmTokenNotSetException(Long memberId) {
        super(String.format("%d번 회원님의 FCM Token이 설정되어 있지 않습니다.", memberId));
        this.memberId = memberId;
    }
}
