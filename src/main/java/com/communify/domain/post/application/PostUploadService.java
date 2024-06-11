package com.communify.domain.post.application;

import com.communify.domain.file.application.FileService;
import com.communify.domain.file.dto.FileUploadRequest;
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
    private final FileService fileService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void uploadPost(PostUploadRequest request) {
        postRepository.insertPost(request);

        Long postId = request.getId();
        fileService.uploadFile(new FileUploadRequest(postId, request.getFileList()));

        Long memberId = request.getMemberId();
        String memberName = request.getMemberName();
        PostUploadEvent event = new PostUploadEvent(memberId, memberName);
        eventPublisher.publishEvent(event);
    }
}
