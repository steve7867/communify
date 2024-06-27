package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PushRequest {

    private final String token;
    private final MessageDto messageDto;
}
