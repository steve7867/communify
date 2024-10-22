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

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostViewScheduler {

    private final PostViewCacheService postViewCacheService;
    private final SqlSessionFactory sqlSessionFactory;

    @Scheduled(cron = "*/30 * * * * *")
    @SchedulerLock(name = SchedulerNames.POST_VIEW_SCHEDULER)
    public void savePostViewCacheToDB() {
        final Map<Long, Integer> map = postViewCacheService.getPostViewCacheAndClear();

        if (map.isEmpty()) {
            return;
        }

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            PostRepository postRepository = sqlSession.getMapper(PostRepository.class);

            map.keySet()
                    .stream()
                    .sorted()
                    .forEach(postId -> {
                        Integer viewCount = map.get(postId);

                        postRepository.incViewCount(postId, viewCount);
                    });

            sqlSession.flushStatements();
        }
    }
}
