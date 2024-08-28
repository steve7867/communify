package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class PushInfoForPostUpload extends PushInfo {

    private final String token;

    private final String writerName;

    @Override
    public Boolean isPushable() {
        return isTokenExisting();
    }

    private Boolean isTokenExisting() {
        return Objects.nonNull(token);
    }

    @Override
    MessageDto makeMessageDto() {
        return MessageDto.forPostUpload(token, writerName);
    }
}
