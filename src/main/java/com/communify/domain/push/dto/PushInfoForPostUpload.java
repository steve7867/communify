package com.communify.domain.push.dto;

import lombok.Getter;

@Getter
public class PushInfoForPostUpload extends PushInfo {

    private final String writerName;

    public PushInfoForPostUpload(final String token, final String writerName) {
        super(token);
        this.writerName = writerName;
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forPostUpload(token, writerName);
    }
}
