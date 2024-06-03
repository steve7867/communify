package com.communify.domain.verification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class VerificationRequest {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{8}$", message = "적절하지 않은 인증 코드입니다.")
    private final String code;
}
