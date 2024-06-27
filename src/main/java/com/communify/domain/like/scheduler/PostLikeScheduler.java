package com.communify.domain.like.scheduler;

import com.communify.domain.like.dao.LikeRepository;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.util.BulkInsertUtil;
import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostLikeScheduler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final LikeRepository likeRepository;

    @Scheduled(cron = "*/5 * * * * *")
    @SchedulerLock(name = "PostLikeScheduler_applyPostLikeToDB", lockAtLeastFor = "5s", lockAtMostFor = "7s")
    public void applyPostLikeToDB() {
        final List<LikeRequest> totalLikeRequestList = new ArrayList<>();

        final Set<String> keySet = redisTemplate.keys(CacheNames.POST_LIKE + "*");

        for (String cacheKey : Objects.requireNonNull(keySet)) {
            final Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));

            final List<Object> result = redisTemplate.execute(new SessionCallback<>() {

                @Override
                public List<Object> execute(final RedisOperations operations) throws DataAccessException {
                    operations.multi();

                    operations.opsForSet().members(cacheKey);
                    operations.delete(cacheKey);

                    return operations.exec();
                }
            });

            final Set<Integer> memberIdSet = (Set<Integer>) result.get(0);
            final List<LikeRequest> likeRequestList = memberIdSet.stream()
                    .mapToLong(Integer::longValue)
                    .mapToObj(memberId -> LikeRequest.builder()
                            .postId(postId)
                            .memberId(memberId)
                            .build()
                    ).toList();

            totalLikeRequestList.addAll(likeRequestList);
        }

        final int size = totalLikeRequestList.size();
        int lIdx = 0;
        while (lIdx < size) {
            final int rIdx = Math.min(lIdx + 100, size);
            final List<LikeRequest> subList = totalLikeRequestList.subList(lIdx, rIdx);

            BulkInsertUtil.forceBulkInsert(likeRepository, subList);

            lIdx = rIdx;
        }
    }
}
