package com.communify.domain.verification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerificationRequest {

    @NotBlank(message = "공백은 허용되지 않습니다.")
    @Pattern(regexp = "^[a-z0-9]{8}$", message = "적절하지 않은 인증 코드입니다.")
    private final String code;
}
