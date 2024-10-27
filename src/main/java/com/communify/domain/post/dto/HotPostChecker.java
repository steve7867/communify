package com.communify.domain.post.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HotPostChecker {

    private final Boolean isHot;
    private final Integer likeCount;
    private final Integer commentCount;

    public Boolean isEligible() {
        return !isHot && likeCount >= 100 && commentCount >= 100;
    }
}
