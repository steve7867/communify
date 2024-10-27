package com.communify.domain.push.dto;

import lombok.Getter;

@Getter
public class PushInfoForComment extends PushInfo {

    public PushInfoForComment(String token) {
        super(token);
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forComment(token);
    }
}
