package com.communify.domain.member.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends EntityNotFoundException {

    public static final String EMAIL_NOT_EXISTING_MESSAGE_FORMAT = "이메일이 %s인 회원은 존재하지 않습니다.";
    public static final String MEMBER_NOT_EXISTING_MESSAGE_FORMAT = "회원 번호가 %d인 회원은 존재하지 않습니다.";
    private final String email;
    private final Long memberId;

    public MemberNotFoundException(final String email) {
        super(String.format(EMAIL_NOT_EXISTING_MESSAGE_FORMAT, email));
        this.email = email;
        this.memberId = null;
    }

    public MemberNotFoundException(final Long memberId) {
        super(String.format(MEMBER_NOT_EXISTING_MESSAGE_FORMAT, memberId));
        this.memberId = memberId;
        this.email = null;
    }
}
