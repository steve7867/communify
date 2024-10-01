package com.communify.domain.push.dto;

public class PushInfoForFollow extends PushInfo {

    private final String followerName;

    public PushInfoForFollow(final String token, final String followerName) {
        super(token);
        this.followerName = followerName;
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forFollow(token, followerName);
    }
}
