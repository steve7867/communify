package com.communify.domain.like.application;

import com.communify.domain.like.dao.LikeRepository;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.application.CacheService;
import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final CacheService cacheService;
    private final LikeRepository likeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void like(LikeRequest request) {
        String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, request.getPostId());
        cacheService.addToSet(cacheKey, request.getMemberId());

        eventPublisher.publishEvent(new LikeEvent(request));
    }

    public void cancelLike(Long postId, Long memberId) {
        String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, postId);

        boolean removed = cacheService.removeFromSet(cacheKey, memberId);

        if (removed) {
            return;
        }

        likeRepository.deleteLike(postId, memberId);
    }
}
