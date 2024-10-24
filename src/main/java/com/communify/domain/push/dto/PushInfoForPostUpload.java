package com.communify.domain.push.dto;

import lombok.Getter;

@Getter
public class PushInfoForPostUpload extends PushInfo {

    private final String writerName;

    public PushInfoForPostUpload(String token, String writerName) {
        super(token);
        this.writerName = writerName;
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forPostUpload(token, writerName);
    }
}
