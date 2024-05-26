package com.communify.domain.like.application;

import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.application.CacheService;
import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final CacheService cacheService;

    public void like(LikeRequest request) {
        String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, request.getPostId());
        cacheService.addToSet(cacheKey, request.getMemberId());

        // todo: 이벤트 처리
    }
}
