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
        final String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_VIEW_BUFFER, postId);

        redisTemplate.opsForValue().increment(key);
    }

    public Map<Long, Integer> fetchAndRemoveViewCache() {
        final ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(CacheNames.POST_VIEW_BUFFER + "*")
                .count(300)
                .build();

        try (final Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            final List<String> keyList = cursor.stream().toList();

            final List<Object> result = executePipelined(keyList);

            return generatePostViewMap(keyList, result);
        }
    }

    private List<Object> executePipelined(final List<String> keyList) {
        return redisTemplate.executePipelined(new SessionCallback<>() {

            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                for (String cacheKey : keyList) {
                    try {
                        operations.multi();

                        operations.opsForValue().get(cacheKey);
                        operations.delete(cacheKey);

                        operations.exec();
                    } catch (RuntimeException e) {
                        operations.discard();
                        throw e;
                    }
                }

                return null;
            }
        });
    }

    private Map<Long, Integer> generatePostViewMap(final List<String> keyList,
                                                   final List<Object> result) {

        final Map<Long, Integer> postViewMap = new TreeMap<>();

        for (int i = 0; i < keyList.size(); i++) {
            final String cacheKey = keyList.get(i);
            final Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));

            final List<Object> txResultList = (List<Object>) result.get(i);
            final Integer viewCount = (Integer) txResultList.get(0);

            postViewMap.put(postId, viewCount);
        }

        return postViewMap;
    }
}
