package com.communify.domain.push.dto;

import lombok.Getter;

@Getter
public class PushInfoForComment extends PushInfo {

    private final String commentContent;
    private final String commentWriterName;

    public PushInfoForComment(final String token, final String commentContent, final String commentWriterName) {
        super(token);
        this.commentContent = commentContent;
        this.commentWriterName = commentWriterName;
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forComment(token, commentWriterName, commentContent);
    }
}
