package com.communify.domain.post;

import com.communify.global.application.cache.PostViewCacheService;
import com.communify.global.util.SchedulerNames;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostViewSyncScheduler {

    private final PostViewCacheService postViewCacheService;
    private final SqlSessionFactory sqlSessionFactory;

    @Scheduled(cron = "*/30 * * * * *")
    @SchedulerLock(name = SchedulerNames.POST_VIEW_SYNC)
    public void syncCachedPostViewsWithDB() {
        final Map<Long, Integer> postViewMap = postViewCacheService.fetchAndRemoveViewCache();

        if (postViewMap.isEmpty()) {
            return;
        }

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            PostRepository postRepository = sqlSession.getMapper(PostRepository.class);

            postViewMap.keySet()
                    .stream()
                    .sorted()
                    .forEach(postId -> {
                        Integer viewCount = postViewMap.get(postId);

                        postRepository.incViewCount(postId, viewCount);
                    });

            sqlSession.flushStatements();
        }
    }
}
