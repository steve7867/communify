package com.communify.domain.hotpost.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AllHotPostSearchCondition {

    private final Long lastPostId;
    private final Integer searchSize = 20;
}
