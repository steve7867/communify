package com.communify.domain.like.application;

import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.application.cache.CacheService;
import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final CacheService cacheService;
    private final ApplicationEventPublisher eventPublisher;

    public void like(final LikeRequest request) {
        final String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, request.getPostId());
        cacheService.addToSet(cacheKey, request.getMemberId());

        eventPublisher.publishEvent(new LikeEvent(request));
    }

    }
}
