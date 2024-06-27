package com.communify.global.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean addToSet(final String key, final Object value) {
        final Long addCount = redisTemplate.opsForSet().add(key, value);

        return addCount > 0;
    }

    public boolean removeFromSet(final String key, final Object value) {
        final Long removeCount = redisTemplate.opsForSet().remove(key, value);

        return removeCount > 0;
    }
}
