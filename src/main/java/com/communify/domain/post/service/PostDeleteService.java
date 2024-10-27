package com.communify.domain.post.service;

import com.communify.domain.post.PostRepository;
import com.communify.domain.post.exception.InvalidPostAccessException;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDeleteService {

    private final PostRepository postRepository;
    private final PostAttachmentService postAttachmentService;

    @Transactional
    @CacheEvict(cacheNames = CacheNames.POST_DETAIL, key = "#postId")
    public void deletePost(Long postId, Long requesterId) {
        boolean canDelete = postRepository.isWrittenBy(postId, requesterId);
        if (!canDelete) {
            throw new InvalidPostAccessException(postId, requesterId);
        }

        postAttachmentService.deleteFiles(postId);

        postRepository.deletePost(postId, requesterId);
    }
}
