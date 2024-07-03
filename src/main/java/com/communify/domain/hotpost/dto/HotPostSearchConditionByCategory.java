package com.communify.domain.hotpost.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HotPostSearchConditionByCategory {

    private final Long categoryId;
    private final Integer searchSize = 3;
}
