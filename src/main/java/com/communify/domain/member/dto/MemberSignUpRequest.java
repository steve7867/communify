package com.communify.domain.member.dto;

import com.communify.global.util.PasswordEncryptor;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class MemberSignUpRequest {

    @Email
    @NotBlank
    @Size(min = 5, max = 50)
    private final String email;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=<>?/{}|~]{7,20}$",
            message = "숫자, 영문 대소문자, 공백을 제외한 특수 문자 7 ~ 20자리를 입력해주세요.")
    private final String password;

    private final String hashed;

    @NotBlank
    @Size(min = 1, max = 10)
    private final String name;

    @JsonCreator
    public MemberSignUpRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;

        this.hashed = PasswordEncryptor.encrypt(password);
    }
}
