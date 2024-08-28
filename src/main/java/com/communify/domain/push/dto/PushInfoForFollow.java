package com.communify.domain.push.dto;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class PushInfoForFollow extends PushInfo {

    private final String token;

    private final String followerName;

    @Override
    public Boolean isPushable() {
        return isTokenExisting();
    }

    private Boolean isTokenExisting() {
        return Objects.nonNull(token);
    }

    @Override
    MessageDto makeMessageDto() {
        return MessageDto.forFollow(token, followerName);
    }
}
