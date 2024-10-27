package com.communify.domain.post.scheduler;

import com.communify.global.application.CacheService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LikeEvictScheduler {

    private final CacheService cacheService;

    @Scheduled(cron = "0 * * * * *")
    @SchedulerLock(name = "LikeEvict")
    public void evictLike() {
        Map<Long, LocalDateTime> map = cacheService.fetchPostCreatedAt();

        List<Long> filterdIdList = map.keySet()
                .stream()
                .filter(postId -> {
                    LocalDateTime createdDateTime = map.get(postId);

                    return Duration.between(createdDateTime, LocalDateTime.now()).toDays() >= 1;
                })
                .toList();

        cacheService.deletePostLike(filterdIdList);
        cacheService.deletePostCreatedAt(filterdIdList);
    }
}
