package com.communify.domain.member.dto;

import java.time.LocalDateTime;

public class MemberInfo {

    private final Long id;
    private final String email;

    private final String hashed;
    private final String name;
    private final LocalDateTime createdDateTime;
}
