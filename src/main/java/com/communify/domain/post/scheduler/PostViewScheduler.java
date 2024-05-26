package com.communify.domain.post.scheduler;

import com.communify.domain.post.dao.PostRepository;
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

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostViewScheduler {

    private final RedisTemplate<String, Object> redisTemplate;
    private final PostRepository postRepository;

    @Scheduled(cron = "*/5 * * * * *")
    @SchedulerLock(name = "PostViewScheduler_applyPostViewToDB", lockAtLeastFor = "5s", lockAtMostFor = "7s")
    public void applyPostViewToDB() {
        Set<String> keySet = redisTemplate.keys(CacheNames.POST_VIEW + "*");

        for (String cacheKey : Objects.requireNonNull(keySet)) {
            List<Object> result = redisTemplate.execute(new SessionCallback<>() {

                @Override
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();

                    operations.opsForSet().size(cacheKey);
                    operations.delete(cacheKey);

                    return operations.exec();
                }
            });

            Long view = (Long) result.get(0);
            Long postId = Long.valueOf(CacheKeyUtil.extractKeyId(cacheKey));

            postRepository.incrementView(postId, view);
        }
    }
}
