package com.communify.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberInfoForLogin {

    private final Long id;
    private final String hashed;
    private final String name;
}
