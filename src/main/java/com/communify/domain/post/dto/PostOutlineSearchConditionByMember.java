package com.communify.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostOutlineSearchConditionByMember {

    private final Long memberId;
    private final Long lastPostId;
    private final Integer searchSize = 20;
}
