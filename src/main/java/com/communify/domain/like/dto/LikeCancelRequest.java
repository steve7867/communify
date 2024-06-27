package com.communify.domain.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeCancelRequest {

    private final Long postId;
    private final Long memberId;
}
