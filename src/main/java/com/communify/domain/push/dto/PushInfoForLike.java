package com.communify.domain.push.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class PushInfoForLike extends PushInfo {

    private final Long postId;
    private final Long postWriterId;

    private final Long likerId;
    private final String likerName;

    private final String pushState;

    public PushInfoForLike(final Long postId,
                           final Long postWriterId,
                           final String token,
                           final Long likerId,
                           final String likerName,
                           final String pushState) {

        super(token);
        this.postId = postId;
        this.postWriterId = postWriterId;
        this.likerId = likerId;
        this.likerName = likerName;
        this.pushState = pushState;
    }

    @Override
    public Boolean isPushable() {
        return isTokenExisting()
                && !isPostWriterEqualToLiker()
                && !isAlreadySent();
    }

    private Boolean isPostWriterEqualToLiker() {
        return Objects.equals(postWriterId, likerId);
    }

    private Boolean isAlreadySent() {
        return Objects.equals(pushState, "sent");
    }

    @Override
    protected MessageDto makeMessageDto() {
        return MessageDto.forPostLike(token, likerName);
    }
}
