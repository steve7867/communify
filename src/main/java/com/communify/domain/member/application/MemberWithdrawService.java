package com.communify.domain.member.application;

import com.communify.domain.member.dto.MemberWithdrawRequest;

public interface MemberWithdrawService {

    void withdraw(MemberWithdrawRequest request, Long memberId);
}
