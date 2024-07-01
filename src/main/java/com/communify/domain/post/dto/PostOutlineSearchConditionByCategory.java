package com.communify.domain.post.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class PostOutlineSearchConditionByCategory {

    private final Long categoryId;
    private final Long lastPostId;
    private final Integer searchSize;

    public PostOutlineSearchConditionByCategory(final Long categoryId, final Long lastPostId) {
        this(categoryId, lastPostId, 20);
    }

    private PostOutlineSearchConditionByCategory(final Long categoryId, final Long lastPostId, final Integer searchSize) {
        this.categoryId = categoryId;
        this.lastPostId = lastPostId;
        this.searchSize = searchSize;
    }

    public Boolean shouldSearchForHotPosts() {
        return Objects.isNull(lastPostId);
    }

    public PostOutlineSearchConditionByCategory setSearchSize(final Integer newSearchSize) {
        if (newSearchSize < 0) {
            throw new IllegalArgumentException("조회 사이즈는 양수여야 합니다.");
        }

        return new PostOutlineSearchConditionByCategory(categoryId, lastPostId, newSearchSize);
    }
}
