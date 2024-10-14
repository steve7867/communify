package com.communify.domain.like;

import com.communify.domain.like.service.LikeSaveService;
import com.communify.global.application.cache.PostLikeCacheService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostLikeScheduler {

    private final PostLikeCacheService postLikeCacheService;
    private final LikeSaveService likeSaveService;

    @Scheduled(cron = "*/5 * * * * *")
    @SchedulerLock(name = "PostLikeScheduler_applyPostLikeCacheToDB", lockAtMostFor = "4s")
    public void applyPostLikeCacheToDB() {
        final Map<Long, List<Long>> postLikeMap = postLikeCacheService.getPostLikeCacheAsMapAndClear();

        postLikeMap.keySet()
                .stream()
                .sorted()
                .forEach(postId -> {
                    final List<Long> likerIdList = postLikeMap.get(postId);

                    if (likerIdList.isEmpty()) {
                        return;
                    }

                    likeSaveService.savePostLike(postId, likerIdList);
                });
    }
}
