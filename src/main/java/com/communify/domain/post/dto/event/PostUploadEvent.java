package com.communify.domain.post.dto.event;

import com.communify.domain.post.dto.PostUploadRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUploadEvent {

    private final PostUploadRequest postUploadRequest;
}
