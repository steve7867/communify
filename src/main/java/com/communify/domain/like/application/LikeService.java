package com.communify.domain.like.application;

import com.communify.domain.like.dao.LikeRepository;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.application.cache.PostLikeCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeCacheService postLikeCacheService;
    private final LikeRepository likeRepository;

    public void like(final LikeRequest request) {
        postLikeCacheService.cacheLike(request);
    }

    @Transactional(readOnly = true)
    public Boolean isLiking(Long postId, Long likerId) {
        return likeRepository.findLiking(postId, likerId);
    }
}
