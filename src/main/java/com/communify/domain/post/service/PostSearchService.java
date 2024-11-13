package com.communify.domain.post.service;

import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.repository.PostRepository;
import com.communify.global.application.CacheService;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Cacheable(cacheNames = CacheNames.POST_DETAIL,
            key = "#postId",
            unless = "#result != null " +
                    "&& T(java.time.Duration).between(#result.createdDateTime, T(java.time.LocalDateTime).now()).toHours() > 24")
    public PostDetail getPostDetail(Long postId) {
        PostDetail postDetail = postRepository.findPostDetail(postId);
        cacheService.incView(postId);
        return postDetail;
    }
}
