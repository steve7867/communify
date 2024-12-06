package com.communify.domain.post.service;

import com.communify.domain.post.dto.LikeEvent;
import com.communify.domain.post.exception.AlreadyLikedException;
import com.communify.domain.post.repository.PostRepository;
import com.communify.global.application.CacheService;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostEditService {

    private final CacheService cacheService;
    private final PostRepository postRepository;
    private final PostAttachmentService postAttachmentService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @CacheEvict(cacheNames = CacheNames.POST_DETAIL, key = "#postId")
    public void editPost(Long postId,
                         String title,
                         String content,
                         List<MultipartFile> multipartFileList,
                         Long categoryId,
                         Long requesterId) {

        postRepository.editPost(postId, title, content, categoryId, requesterId);
        postAttachmentService.updateFiles(postId, multipartFileList);
    }

    public void like(Long postId, Long likerId) {
        Boolean alreadyLiked = cacheService.cacheLike(postId, likerId);
        if (alreadyLiked) {
            throw new AlreadyLikedException();
        }
        postRepository.incLikeCount(postId, 1);

        eventPublisher.publishEvent(new LikeEvent(postId));
    }
}
