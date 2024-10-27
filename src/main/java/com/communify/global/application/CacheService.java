package com.communify.global.application;

import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public Boolean cacheLike(Long postId, Long likerId) {
        String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, postId);
        Long addedCount = redisTemplate.opsForSet().add(cacheKey, likerId);
        return addedCount == 0L;
    }

    public void incView(Long postId) {
        String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_VIEW, postId);
        redisTemplate.opsForValue().increment(key);
    }

    public Map<Long, Integer> fetchAndRemoveViewCache() {
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(CacheNames.POST_VIEW + "*")
                .count(300)
                .build();

        List<String> keyList;
        try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            keyList = cursor.stream().toList();
        }
        List<Object> result = getValuesWithPipeline(keyList);

        Map<Long, Integer> postViewMap = new TreeMap<>();
        for (int i = 0; i < keyList.size(); i++) {
            String cacheKey = keyList.get(i);
            Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));

            Integer viewCount = (Integer) result.get(i);

            postViewMap.put(postId, viewCount);
        }
        return postViewMap;
    }

    public void setPostCreatedAt(Long postId) {
        String cacheKey = CacheKeyUtil.makeCacheKey(CacheNames.POST_CREATED_AT, postId);
        redisTemplate.opsForValue().set(cacheKey, LocalDateTime.now());
    }

    public Map<Long, LocalDateTime> fetchPostCreatedAt() {
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(CacheNames.POST_CREATED_AT + "*")
                .count(300)
                .build();

        List<String> keyList;
        try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            keyList = cursor.stream().toList();
        }
        List<Object> result = getValuesWithPipeline(keyList);

        Map<Long, LocalDateTime> map = new HashMap<>(keyList.size());
        for (int i = 0; i < keyList.size(); i++) {
            String cacheKey = keyList.get(i);
            Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));

            LocalDateTime createdDateTime = (LocalDateTime) result.get(i);
            map.put(postId, createdDateTime);
        }
        return map;
    }

    private List<Object> getValuesWithPipeline(List<String> keyList) {
        return redisTemplate.executePipelined(new SessionCallback<>() {

            @Override
            public List<Object> execute(RedisOperations ops) throws DataAccessException {
                ValueOperations valueOps = ops.opsForValue();
                keyList.forEach(valueOps::getAndDelete);

                return null;
            }
        });
    }

    public void deletePostLike(List<Long> postIdList) {
        List<String> keyList = postIdList.stream()
                .map(postId -> CacheKeyUtil.makeCacheKey(CacheNames.POST_LIKE, postId))
                .toList();

        redisTemplate.delete(keyList);
    }

    public void deletePostCreatedAt(List<Long> postIdList) {
        List<String> keyList = postIdList.stream()
                .map(postId -> CacheKeyUtil.makeCacheKey(CacheNames.POST_CREATED_AT, postId))
                .toList();

        redisTemplate.delete(keyList);
    }
}
