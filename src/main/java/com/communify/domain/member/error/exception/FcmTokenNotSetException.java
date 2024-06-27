package com.communify.domain.member.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class FcmTokenNotSetException extends EntityNotFoundException {

    public static final String MESSAGE_FORMAT = "%d번 회원님의 FCM Token이 설정되어 있지 않습니다.";
    private final Long memberId;

    public FcmTokenNotSetException(final Long memberId) {
        super(String.format(MESSAGE_FORMAT, memberId));
        this.memberId = memberId;
    }
}
