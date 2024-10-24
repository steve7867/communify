package com.communify.domain.post.service;

import com.communify.domain.post.PostRepository;
import com.communify.domain.post.exception.InvalidPostAccessException;
import com.communify.global.application.cache.PostViewCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostEditService {

    private final PostRepository postRepository;
    private final PostAttachmentService postAttachmentService;
    private final PostViewCacheService postViewCacheService;

    public void incrementView(Long postId) {
        postViewCacheService.incrementView(postId);
    }

    @Transactional
    public void editPost(Long postId,
                         String title,
                         String content,
                         List<MultipartFile> multipartFileList,
                         Long categoryId,
                         Long requesterId) {

        boolean isEdited = postRepository.editPost(postId, title, content, categoryId, requesterId);
        if (!isEdited) {
            throw new InvalidPostAccessException(postId, requesterId);
        }

        postAttachmentService.updateFiles(postId, multipartFileList);
    }

    public void promoteToHot(Long postId) {
        postRepository.promoteToHot(postId);
    }
}
