package com.communify.domain.like.service;

import com.communify.domain.like.LikeRepository;
import com.communify.global.application.cache.PostLikeCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeCacheService postLikeCacheService;
    private final LikeRepository likeRepository;

    public void like(Long postId, Long likerId) {
        postLikeCacheService.cacheLike(postId, likerId);
    }

    @Transactional(readOnly = true)
    public Boolean isLiking(Long postId, Long likerId) {
        return likeRepository.findLiking(postId, likerId);
    }
}
