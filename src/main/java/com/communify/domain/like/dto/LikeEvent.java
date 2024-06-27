package com.communify.domain.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeEvent {

    private final LikeRequest likeRequest;

    public String getMemberName() {
        return likeRequest.getMemberName();
    }

    public Long getMemberId() {
        return likeRequest.getMemberId();
    }

    public Long getPostId() {
        return likeRequest.getPostId();
    }
}
