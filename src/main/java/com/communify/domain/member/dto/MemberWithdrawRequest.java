package com.communify.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberWithdrawRequest {

    @NotBlank
    private final String password;
}
