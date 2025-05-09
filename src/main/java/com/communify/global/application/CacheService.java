package com.communify.global.application;

import com.communify.domain.post.dto.PostDetail;
import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HyperLogLogOperations;
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
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cachePostDetail(Long postId, PostDetail postDetail) {
        String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_DETAIL, postId);
        redisTemplate.opsForValue().set(key, postDetail);
    }

    public PostDetail getPostDetail(Long postId) {
        String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_DETAIL, postId);
        return (PostDetail) redisTemplate.opsForValue().get(key);
    }

    public Boolean cacheLike(Long postId, Long userId) {
        Long result = redisTemplate.opsForHyperLogLog().add(CacheNames.POST_LIKE, postId, userId);
        return result == 0L;
    }

    public void deleteLikeHyperLogLog() {
        redisTemplate.delete(CacheNames.POST_LIKE);
    }

    public void incView(Long postId, Long userId) {
        String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_VIEW, postId);
        redisTemplate.opsForHyperLogLog().add(key, userId);
    }

    public Map<Long, Integer> fetchAndRemoveView() {
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(CacheNames.POST_VIEW + "*")
                .count(300)
                .build();

        List<String> keyList;
        try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            keyList = cursor.stream().toList();
        }

        List<Object> result = redisTemplate.executePipelined(new SessionCallback<>() {

            @Override
            public List<Object> execute(RedisOperations ops) throws DataAccessException {
                HyperLogLogOperations hyperLogLogOps = ops.opsForHyperLogLog();
                keyList.forEach(key -> {
                    hyperLogLogOps.size(key);
                    hyperLogLogOps.delete(key);
                });

                return null;
            }
        });

        Map<Long, Integer> postViewMap = new TreeMap<>();
        for (int i = 0; i < keyList.size(); i++) {
            String cacheKey = keyList.get(i);
            Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));

            Integer view = (Integer) result.get(i);

            postViewMap.put(postId, view);
        }
        return postViewMap;
    }

    public void addDeletedUser(Long userId) {
        redisTemplate.opsForSet().add(CacheNames.DELETED_USER, userId);
    }

    public boolean isDeletedUser(Long userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(CacheNames.DELETED_USER, userId));
    }

    public void removeFromDeletedUser(Long userId) {
        redisTemplate.opsForSet().remove(CacheNames.DELETED_USER, userId);
    }
}
