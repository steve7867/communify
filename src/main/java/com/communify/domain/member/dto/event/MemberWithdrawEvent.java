package com.communify.domain.member.dto.event;

import com.communify.domain.member.dto.MemberWithdrawRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberWithdrawEvent {

    private final MemberWithdrawRequest request;

    public Long getMemberId() {
        return request.getMemberId();
    }
}
