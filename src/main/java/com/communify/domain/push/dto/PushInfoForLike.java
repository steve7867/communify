package com.communify.domain.push.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(force = true)
public class PushInfoForLike extends PushInfo {

    private final Long postId;
    private final Long postWriterId;
    private final String token;

    private final Long likerId;
    private final String likerName;

    private final String pushState;

    @Override
    public Boolean isPushable() {
        return isTokenExist()
                && !isPostWriterEqualToLiker()
                && !isAlreadySent();
    }

    private Boolean isTokenExist() {
        return Objects.nonNull(token);
    }

    private Boolean isPostWriterEqualToLiker() {
        return Objects.equals(postWriterId, likerId);
    }

    private Boolean isAlreadySent() {
        return Objects.equals(pushState, "sent");
    }

    @Override
    MessageDto makeMessageDto() {
        return MessageDto.forPostLike(token, likerName);
    }
}
