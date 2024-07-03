package com.communify.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberSearchRequest {

    private final Long memberId;
    private final Long searcherId;
}
