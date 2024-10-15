package com.communify.domain.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikerInfo {

    private final Long likerId;
    private final String likerName;
}
