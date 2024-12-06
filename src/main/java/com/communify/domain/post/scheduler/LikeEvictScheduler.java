package com.communify.domain.post.scheduler;

import com.communify.global.application.CacheService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeEvictScheduler {

    private final CacheService cacheService;

    @Scheduled(cron = "0 0 0 * * *")
    @SchedulerLock(name = "deleteLike")
    public void deleteLikeCache() {
        cacheService.deleteLikeHyperLogLog();
    }
}
