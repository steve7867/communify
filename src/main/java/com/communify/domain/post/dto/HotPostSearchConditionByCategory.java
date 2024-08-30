package com.communify.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HotPostSearchConditionByCategory {

    public static final Integer BASIC_SEARCH_SIZE = 3;

    private final Long categoryId;
    private final Integer searchSize = BASIC_SEARCH_SIZE;
}
