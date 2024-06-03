package com.communify.domain.verification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class CodeIssueRequest {

    @Email
    @NotBlank
    @Size(min = 5, max = 50)
    private final String email;
}
