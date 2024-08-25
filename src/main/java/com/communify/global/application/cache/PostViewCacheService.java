package com.communify.global.application.cache;

import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class PostViewCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void incrementView(final Long postId) {
        final String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_VIEW, postId);
        redisTemplate.opsForValue().increment(key);
    }

    public Map<Long, Integer> getPostViewCacheAsMapAndClear() {
        final ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(CacheNames.POST_VIEW + "*")
                .count(300)
                .build();

        try (final Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            final List<String> keyList = cursor.stream().toList();

            final Map<Long, Integer> viewMap = new TreeMap<>();

            for (String cacheKey : keyList) {
                final List<Object> result = redisTemplate.execute(new SessionCallback<>() {

                    @Override
                    public List<Object> execute(final RedisOperations operations) throws DataAccessException {
                        operations.multi();

                        operations.opsForValue().get(cacheKey);
                        operations.delete(cacheKey);

                        return operations.exec();
                    }
                });

                final Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));
                final Integer viewCount = (Integer) result.get(0);

                viewMap.put(postId, viewCount);
            }

            return viewMap;
        }
    }
}
