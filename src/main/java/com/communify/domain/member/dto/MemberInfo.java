package com.communify.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class MemberInfo {

    private final Long id;
    private final String email;

    @JsonIgnore
    private final String hashed;
    private final String name;
    private final LocalDateTime createdDateTime;
}
