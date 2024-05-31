package com.communify.domain.push.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {

    private final String title;
    private final String body;
}
