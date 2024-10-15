package com.communify.global.application.cache;

import com.communify.domain.like.dto.LikerInfo;
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

    public void cacheLike(final Long postId, final Long likerId, final String likerName) {
        final String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, postId);

        redisTemplate.opsForSet().add(cacheKey, new LikerInfo(likerId, likerName));
    }

    public Map<Long, List<LikerInfo>> getPostLikeCacheAndClear() {
        final ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(CacheNames.POST_LIKE + "*")
                .count(300)
                .build();

        final List<String> keyList;
        try (final Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            keyList = cursor.stream().toList();
        }

        final List<Object> result = executePipelined(keyList);

        return generatePostLikeMap(keyList, result);
    }

    private List<Object> executePipelined(final List<String> keyList) {
        return redisTemplate.executePipelined(new SessionCallback<>() {

            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                for (String cacheKey : keyList) {
                    try {
                        operations.multi();

                        operations.opsForSet().members(cacheKey);
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

    private Map<Long, List<LikerInfo>> generatePostLikeMap(final List<String> keyList,
                                                           final List<Object> result) {

        final Map<Long, List<LikerInfo>> postLikeMap = new HashMap<>(keyList.size());

        for (int i = 0; i < keyList.size(); i++) {
            final String cacheKey = keyList.get(i);
            final Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));

            final List<Object> txResultList = (List<Object>) result.get(i);

            final List<LikerInfo> likerInfoList = ((Set<LikerInfo>) txResultList.get(0))
                    .stream()
                    .toList();

            postLikeMap.put(postId, likerInfoList);
        }

        return postLikeMap;
    }
}
