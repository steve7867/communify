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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostLikeCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheLike(final Long postId, final Long likerId) {
        final String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, postId);

        redisTemplate.opsForZSet().addIfAbsent(cacheKey, likerId, Double.valueOf(likerId));
    }

    public Map<Long, List<Long>> getPostLikeCacheAsMapAndClear() {
        final ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(CacheNames.POST_LIKE + "*")
                .count(300)
                .build();

        try (final Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            final List<String> keyList = cursor.stream().toList();

            final List<Object> result = executePipelined(keyList);

            return generatePostLikeMap(keyList, result);
        }
    }

    private List<Object> executePipelined(final List<String> keyList) {
        return redisTemplate.executePipelined(new SessionCallback<>() {

            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                for (String cacheKey : keyList) {
                    try {
                        operations.multi();

                        operations.opsForZSet().range(cacheKey, 0L, Long.MAX_VALUE);
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

    private Map<Long, List<Long>> generatePostLikeMap(final List<String> keyList,
                                                      final List<Object> result) {

        final Map<Long, List<Long>> postLikeMap = new HashMap<>(keyList.size());

        for (int i = 0; i < keyList.size(); i++) {
            final String cacheKey = keyList.get(i);
            final Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));

            final List<Object> txResultList = (List<Object>) result.get(i);

            final List<Long> likerIdList = ((Set<Integer>) txResultList.get(0))
                    .stream()
                    .map(Integer::longValue)
                    .toList();

            postLikeMap.put(postId, likerIdList);
        }

        return postLikeMap;
    }
}
