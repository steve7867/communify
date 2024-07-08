package com.communify.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostOutlineSearchConditionByCategory {

    public static final Integer BASIC_SEARCH_SIZE = 20;

    private final Long categoryId;
    private final Long lastPostId;
    private final Integer searchSize;

    public PostOutlineSearchConditionByCategory(final Long categoryId, final Long lastPostId) {
        this(categoryId, lastPostId, BASIC_SEARCH_SIZE);
    }

    public Boolean shouldSearchForHotPosts() {
        return Objects.isNull(lastPostId);
    }

    public PostOutlineSearchConditionByCategory setSearchSize(final Integer newSearchSize) {
        if (newSearchSize < 0) {
            throw new IllegalArgumentException("조회 사이즈는 양수 또는 0만 허용됩니다.");
        }

        return new PostOutlineSearchConditionByCategory(categoryId, lastPostId, newSearchSize);
    }
}
