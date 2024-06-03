package com.communify.domain.post.dto.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUploadEvent {

    private final Long memberId;
    private final String memberName;
}
