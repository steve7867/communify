package com.communify.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AllHotPostSearchCondition {

    private static final Integer BASIC_SEARCH_SIZE = 20;

    private final Long lastPostId;
    private final Integer searchSize = BASIC_SEARCH_SIZE;
}
