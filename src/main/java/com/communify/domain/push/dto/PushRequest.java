package com.communify.domain.push.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PushRequest {

    private final String token;
    private final MessageDto messageDto;
}
