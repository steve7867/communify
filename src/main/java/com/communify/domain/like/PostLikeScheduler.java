package com.communify.domain.like;

import com.communify.domain.like.dto.LikerInfo;
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
public class PostLikeScheduler {

    private final PostLikeCacheService postLikeCacheService;
    private final LikeSaveService likeSaveService;

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = SchedulerNames.POST_LIKE_SCHEDULER)
    public void savePostLikeCacheToDB() {
        final Map<Long, List<LikerInfo>> postLikeMap = postLikeCacheService.getPostLikeCacheAndClear();

        postLikeMap.keySet()
                .forEach(postId -> {
                    final List<LikerInfo> likerInfoList = postLikeMap.get(postId);

                    likeSaveService.saveLike(postId, likerInfoList);
                });
    }
}
