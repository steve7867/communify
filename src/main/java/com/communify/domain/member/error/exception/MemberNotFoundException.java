package com.communify.domain.member.error.exception;

import com.communify.global.error.exception.EntityNotFoundException;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends EntityNotFoundException {

    private final String email;
    private final Long memberId;

    public MemberNotFoundException(String email) {
        super(String.format("이메일이 %s인 회원은 존재하지 않습니다.", email));
        this.email = email;
        this.memberId = null;
    }

    public MemberNotFoundException(Long memberId) {
        super(String.format("회원 번호가 %d인 회원은 존재하지 않습니다.", memberId));
        this.memberId = memberId;
        this.email = null;
    }
}
