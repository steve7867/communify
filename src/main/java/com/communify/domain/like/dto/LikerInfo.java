package com.communify.domain.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class LikerInfo {

    private final Long likerId;
    private final String likerName;
}
