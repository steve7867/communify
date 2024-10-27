package com.communify.domain.post.service;

import com.communify.domain.post.PostRepository;
import com.communify.domain.post.dto.PostUploadEvent;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.global.application.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostUploadService {

    private final PostRepository postRepository;
    private final CacheService cacheService;
    private final PostAttachmentService postAttachmentService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void uploadPost(PostUploadRequest request) {
        postRepository.insertPost(request);
        postAttachmentService.savePostAttachments(request.getId(), request.getMultipartFileList());
        cacheService.setPostCreatedAt(request.getId());
        eventPublisher.publishEvent(new PostUploadEvent(request.getWriterId(), request.getWriterName()));
    }
}
