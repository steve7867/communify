package com.communify.domain.push.dto;

import lombok.Getter;

@Getter
public class PushInfoForLike extends PushInfo {

    private final String likerName;

    public PushInfoForLike(final String token,
                           final String likerName) {

        super(token);
        this.likerName = likerName;
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forPostLike(token, likerName);
    }
}
