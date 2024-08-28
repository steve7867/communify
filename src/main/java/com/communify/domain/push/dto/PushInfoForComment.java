package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class PushInfoForComment extends PushInfo {

    private final String token;

    private final String commentContent;
    private final String commentWriterName;

    @Override
    public Boolean isPushable() {
        return isTokenExisting();
    }

    private Boolean isTokenExisting() {
        return Objects.nonNull(token);
    }

    @Override
    MessageDto makeMessageDto() {
        return MessageDto.forComment(token, commentWriterName, commentContent);
    }
}
