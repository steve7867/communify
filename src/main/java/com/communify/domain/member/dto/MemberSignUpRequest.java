package com.communify.domain.member.dto;

import com.communify.global.util.PasswordEncryptor;
import lombok.Getter;

@Getter
public class MemberSignUpRequest {

    private final Long id;
    private final String email;
    private final String name;
    private final String hashed;

    public MemberSignUpRequest(String email, String name, String password) {
        this.id = null;
        this.email = email;
        this.name = name;
        this.hashed = PasswordEncryptor.encrypt(password);
    }
}
