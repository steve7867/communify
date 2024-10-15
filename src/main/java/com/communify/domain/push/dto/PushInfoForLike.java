package com.communify.domain.push.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class PushInfoForLike extends PushInfo {

    private final Long postWriterId;
    private final Long likerId;
    private final String likerName;

    public PushInfoForLike(final Long postWriterId,
                           final String token,
                           final Long likerId,
                           final String likerName) {

        super(token);
        this.postWriterId = postWriterId;
        this.likerId = likerId;
        this.likerName = likerName;
    }

    @Override
    public Boolean isPushable() {
        return isTokenExisting() && !Objects.equals(postWriterId, likerId);
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forPostLike(token, likerName);
    }
}
