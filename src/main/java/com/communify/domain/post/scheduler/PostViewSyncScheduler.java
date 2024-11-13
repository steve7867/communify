package com.communify.domain.post.scheduler;

import com.communify.domain.post.repository.PostRepository;
import com.communify.global.application.CacheService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostViewSyncScheduler {

    private final CacheService cacheService;
    private final SqlSessionFactory sqlSessionFactory;

    @Scheduled(cron = "0 */1 * * * *")
    @SchedulerLock(name = "PostViewSync")
    public void syncCachedPostViewWithDB() {
        Map<Long, Integer> postViewMap = cacheService.fetchAndRemoveViewCache();

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
