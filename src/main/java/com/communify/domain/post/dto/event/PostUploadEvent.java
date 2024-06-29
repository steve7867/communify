package com.communify.domain.post.dto.event;

import com.communify.domain.post.dto.PostUploadRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostUploadEvent {

    private final PostUploadRequest request;

    public Long getWriterId() {
        return request.getMemberId();
    }

    public String getWriterName() {
        return request.getMemberName();
    }
}
