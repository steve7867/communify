package com.communify.domain.push.dto;

import lombok.Getter;

@Getter
public class PushInfoForLike extends PushInfo {

    public PushInfoForLike(String token) {
        super(token);
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forPostLike(token);
    }
}
