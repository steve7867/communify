package com.communify.domain.post.service;

import com.communify.domain.post.PostRepository;
import com.communify.domain.post.exception.InvalidPostAccessException;
import com.communify.global.application.cache.PostViewCacheService;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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

    public void incrementView(final Long postId) {
        postViewCacheService.incrementView(postId);
    }

    @Transactional
    public void editPost(final Long postId,
                         final String title,
                         final String content,
                         final List<MultipartFile> multipartFileList,
                         final Long categoryId,
                         final Long requesterId) {

        final boolean isEdited = postRepository.editPost(postId, title, content, categoryId, requesterId);
        if (!isEdited) {
            throw new InvalidPostAccessException(postId, requesterId);
        }

        postAttachmentService.updateFiles(postId, multipartFileList);
    }
}
