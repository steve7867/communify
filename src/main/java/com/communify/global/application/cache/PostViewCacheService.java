package com.communify.global.application.cache;

import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class PostViewCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void incrementView(final Long postId) {
        final String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_VIEW, postId);
        redisTemplate.opsForValue().increment(key);
    }

    public Map<Long, Integer> getAllOfPostViewCountAndClear() {
        final Set<String> keySet = redisTemplate.keys(CacheNames.POST_VIEW + "*");

        final Map<Long, Integer> map = new TreeMap<>();

        for (String cacheKey : Objects.requireNonNull(keySet)) {
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
            final Integer view = (Integer) result.get(0);

            map.put(postId, view);
        }

        return map;
    }
}
