package com.communify.domain.like.scheduler;

import com.communify.domain.like.application.LikeSaveService;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.application.cache.PostLikeCacheService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostLikeScheduler {

    private final PostLikeCacheService postLikeCacheService;
    private final LikeSaveService likeSaveService;

    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(cron = "*/5 * * * * *")
    @SchedulerLock(name = "PostLikeScheduler_applyPostLikeCacheToDB",
            lockAtLeastFor = "5s",
            lockAtMostFor = "7s")
    public void applyPostLikeCacheToDB() {
        final Map<Long, List<Long>> postLikeMap = postLikeCacheService.getPostLikeCacheAsMapAndClear();

        postLikeMap.keySet()
                .stream()
                .sorted()
                .forEach(postId -> {
                    final List<Long> likerIdList = postLikeMap.get(postId);

                    final List<LikeRequest> likeRequestList = likerIdList
                            .stream()
                            .map(likerId -> LikeRequest.builder().postId(postId).likerId(likerId).build())
                            .toList();

                    if (likeRequestList.isEmpty()) {
                        return;
                    }

                    likeSaveService.savePostLike(postId, likeRequestList);
                    eventPublisher.publishEvent(new LikeEvent(postId, likeRequestList));
                });
    }


}
