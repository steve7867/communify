package com.communify.domain.post.service;

import com.communify.domain.post.dto.PostUploadDto;
import com.communify.domain.post.dto.PostUploadEvent;
import com.communify.domain.post.repository.PostRepository;
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
    public void uploadPost(PostUploadDto dto) {
        postRepository.insertPost(dto);
        postAttachmentService.savePostAttachments(dto.getId(), dto.getMultipartFileList());
        cacheService.setPostCreatedAt(dto.getId());
        eventPublisher.publishEvent(new PostUploadEvent(dto.getWriterId(), dto.getWriterName()));
    }
}
