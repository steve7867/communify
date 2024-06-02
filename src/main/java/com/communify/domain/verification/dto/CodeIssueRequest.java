package com.communify.domain.verification.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(onConstructor_ = @JsonCreator)
public class CodeIssueRequest {

    @Email
    @NotBlank
    @Size(min = 5, max = 50)
    private final String email;
}
