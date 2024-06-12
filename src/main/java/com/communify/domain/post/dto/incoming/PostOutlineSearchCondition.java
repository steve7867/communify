package com.communify.domain.post.dto.incoming;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class PostOutlineSearchCondition {

    @Positive
    private final Long categoryId;

    @Positive
    private final Long memberId;

    @Positive
    private final Long lastPostId;

    public Boolean isSearchingByCategory() {
        return Objects.nonNull(categoryId);
    }

    public Boolean isSearchingByMemberId() {
        return Objects.nonNull(memberId);
    }

    public Boolean isSearchingUpperMost() {
        return Objects.isNull(lastPostId);
    }
}
