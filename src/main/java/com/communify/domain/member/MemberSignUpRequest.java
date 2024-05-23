package com.communify.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class MemberSignUpRequest {

    private final String email;
    private final String password;
    private final String hashed;
    private final String name;
}
