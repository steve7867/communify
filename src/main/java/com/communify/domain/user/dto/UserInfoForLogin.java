package com.communify.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInfoForLogin {

    private final Long id;
    private final String hashed;
    private final String name;
}
