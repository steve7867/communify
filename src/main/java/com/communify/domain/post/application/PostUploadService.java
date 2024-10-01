package com.communify.domain.post.application;

import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.post.dto.event.PostUploadEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostUploadService {

    private final PostRepository postRepository;
    private final PostAttachmentService postAttachmentService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void uploadPost(final PostUploadRequest request) {
        postRepository.insertPost(request);

        postAttachmentService.savePostAttachments(request.getId(), request.getMultipartFileList());

        eventPublisher.publishEvent(new PostUploadEvent(request));
    }
}
