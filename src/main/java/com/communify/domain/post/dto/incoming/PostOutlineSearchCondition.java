package com.communify.domain.post.dto.incoming;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostOutlineSearchCondition {

    @NotNull
    @Positive
    private final Long categoryId;

    @Positive
    private final Long lastPostId;
}
