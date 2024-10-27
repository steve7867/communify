package com.communify.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberWithdrawEvent {

    private final Long memberId;
}
