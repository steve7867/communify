package com.communify.domain.post;

import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostViewIncRequest;
import com.communify.global.application.cache.PostViewCacheService;
import com.communify.global.util.HotPostDeterminer;
import com.communify.global.util.SchedulerNames;
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
    @SchedulerLock(name = SchedulerNames.POST_VIEW_SCHEDULER)
    public void savePostViewCacheToDB() {
        final Map<Long, Integer> map = postViewCacheService.getPostViewCacheAndClear();

        final List<PostViewIncRequest> list = map.entrySet()
                .stream()
                .map(entry -> {
                    final Long postId = entry.getKey();
                    final Integer viewCount = entry.getValue();

                    return new PostViewIncRequest(postId, viewCount);
                })
                .toList();

        postRepository.incViewCount(list);

        final List<Long> postIdList = map.keySet().stream().toList();

        final List<PostOutline> postOutlineList = postRepository.findPostOutlineListForHotSwitch(postIdList);
        final List<Long> hotPostCandidateIdList = postOutlineList.stream()
                .filter(postOutline -> !postOutline.getIsHot())
                .filter(HotPostDeterminer::isCandidateForHot)
                .map(PostOutline::getId)
                .toList();

        postRepository.makePostsAsHot(hotPostCandidateIdList);
    }
}
