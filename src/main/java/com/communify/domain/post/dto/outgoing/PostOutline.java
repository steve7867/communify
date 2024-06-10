package com.communify.domain.post.dto.outgoing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class PostOutline {

    private final Long id;
    private final String title;
    private final Long memberId;
    private final String memberName;
    private final Long categoryId;
    private final Long view;
    private final LocalDateTime createdDateTime;
    private final Integer likes;
    private final Boolean hot;
}
