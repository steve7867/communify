package com.communify.domain.post.scheduler;

import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostViewIncrementRequest;
import com.communify.global.application.cache.PostViewCacheService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostViewScheduler {

    private final PostViewCacheService postViewCacheService;
    private final PostRepository postRepository;

    @Scheduled(cron = "*/30 * * * * *")
    @SchedulerLock(name = "PostViewScheduler_applyPostViewToDB", lockAtLeastFor = "5s", lockAtMostFor = "7s")
    public void applyPostViewToDB() {
        final Map<Long, Integer> map = postViewCacheService.getPostViewCacheAsMapAndClear();

        final List<PostViewIncrementRequest> list = map.entrySet()
                .stream()
                .map(entry -> new PostViewIncrementRequest(entry.getKey(), entry.getValue()))
                .toList();

        if (list.isEmpty()) {
            return;
        }

        postRepository.incrementViewCount(list);
    }
}
