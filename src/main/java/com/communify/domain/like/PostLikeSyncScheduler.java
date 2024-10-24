package com.communify.domain.like;

import com.communify.domain.like.service.LikeSaveService;
import com.communify.global.application.cache.PostLikeCacheService;
import com.communify.global.util.SchedulerNames;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostLikeSyncScheduler {

    private final PostLikeCacheService postLikeCacheService;
    private final LikeSaveService likeSaveService;

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = SchedulerNames.POST_LIKE_SYNC)
    public void syncCacheWithDB() {
        Map<Long, List<Long>> postLikeMap = postLikeCacheService.fetchAndRemoveLikeCache();

        for (Long postId : postLikeMap.keySet()) {
            List<Long> likerIdList = postLikeMap.get(postId);
            likeSaveService.saveLike(postId, likerIdList);
        }
    }
}
