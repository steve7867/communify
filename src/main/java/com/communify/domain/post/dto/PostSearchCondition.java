package com.communify.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostSearchCondition {

    @NotNull(message = "카테고리 id를 지정해주세요.")
    @Positive(message = "양수만 허용됩니다.")
    private final Long categoryId;

    @Positive(message = "양수만 허용됩니다.")
    private final Long lastPostId;
}
