package com.communify.domain.post.service;

import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.repository.PostRepository;
import com.communify.global.application.CacheService;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private static final Integer SEARCH_SIZE = 50;

    private final PostRepository postRepository;
    private final CacheService cacheService;

    @Cacheable(cacheNames = CacheNames.HOT_POST_OUTLINES,
            key = "#lastPostId",
            unless = "T(java.time.Duration).between(#result.get(0).createdDateTime, T(java.time.LocalDateTime).now()).toDays() > 3")
    public List<PostOutline> getHotPostOutlines(Long lastPostId) {
        return postRepository.findHotPostOutlines(lastPostId, SEARCH_SIZE);
    }

    @Cacheable(cacheNames = CacheNames.POST_OUTLINES,
            key = "#categoryId + '_' + #lastPostId",
            unless = "T(java.time.Duration).between(#result.get(0).createdDateTime, T(java.time.LocalDateTime).now()).toHours() > 24")
    public List<PostOutline> getPostOutlinesByCategory(Long categoryId, Long lastPostId) {
        return postRepository.findPostOutlinesByCategory(categoryId, lastPostId, SEARCH_SIZE);
    }

    public List<PostOutline> getPostOutlinesByMember(Long writerId, Long lastPostId) {
        return postRepository.findPostOutlinesByMember(writerId, lastPostId, SEARCH_SIZE);
    }

    public PostDetail getPostDetail(Long postId, Long userId) {
        cacheService.incView(postId, userId);

        PostDetail postDetail = cacheService.getPostDetail(postId);
        if (Objects.nonNull(postDetail)) {
            return postDetail;
        }

        postDetail = postRepository.findPostDetail(postId);

        Boolean isHot = postDetail.getIsHot();
        if (isHot && Duration.between(postDetail.getCreatedDateTime(), LocalDateTime.now()).toHours() < 24) {
            cacheService.cachePostDetail(postId, postDetail);
        }

        return postDetail;
    }
}
