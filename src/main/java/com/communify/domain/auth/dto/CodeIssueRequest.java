package com.communify.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CodeIssueRequest {

    @Email(message = "email 형식으로 입력해주세요.")
    @NotBlank(message = "공백은 허용되지 않습니다.")
    private final String email;
}
