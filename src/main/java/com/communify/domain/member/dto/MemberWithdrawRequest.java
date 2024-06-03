package com.communify.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberWithdrawRequest {

    private final String password;
    private final Long memberId;
}
