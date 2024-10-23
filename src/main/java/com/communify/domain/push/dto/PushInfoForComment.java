package com.communify.domain.push.dto;

import lombok.Getter;

@Getter
public class PushInfoForComment extends PushInfo {

    private final String content;
    private final String writerName;

    public PushInfoForComment(String token, String content, String writerName) {
        super(token);
        this.content = content;
        this.writerName = writerName;
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forComment(token, writerName, content);
    }
}
