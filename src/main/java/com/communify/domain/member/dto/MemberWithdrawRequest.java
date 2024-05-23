package com.communify.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class MemberWithdrawRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;
}
