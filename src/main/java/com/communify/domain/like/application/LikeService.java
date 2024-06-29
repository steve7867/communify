package com.communify.domain.like.application;

import com.communify.domain.like.dao.LikeRepository;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.application.cache.PostLikeCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeCacheService postLikeCacheService;
    private final LikeRepository likeRepository;

    public void like(final LikeRequest request) {
        postLikeCacheService.cacheLike(request);
    }

    public Boolean isLiking(Long postId, Long memberId) {
        return likeRepository.findLiking(postId, memberId);
    }
}
