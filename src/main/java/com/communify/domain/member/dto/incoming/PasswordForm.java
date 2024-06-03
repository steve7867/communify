package com.communify.domain.member.dto.incoming;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class PasswordForm {

    @NotBlank
    private String password;
}
