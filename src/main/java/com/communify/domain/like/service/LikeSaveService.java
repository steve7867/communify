package com.communify.domain.like.service;

import com.communify.domain.like.LikeRepository;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LikeSaveService {

    private final SqlSessionFactory sqlSessionFactory;
    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void saveLike(final Long postId, final List<Long> likerIdList) {
        final int[] updateCounts = batchInsertLikes(postId, likerIdList);

        final Integer sum = Arrays.stream(updateCounts).sum();
        postRepository.incLikeCount(postId, sum);

        final List<Long> validLikerIdList = filterValidLikerId(likerIdList, updateCounts);

        eventPublisher.publishEvent(new LikeEvent(postId, validLikerIdList));
    }

    private int[] batchInsertLikes(Long postId, List<Long> likerIdList) {
        try (final SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            final LikeRepository likeRepository = sqlSession.getMapper(LikeRepository.class);

            for (Long likerId : likerIdList) {
                likeRepository.insertLike(postId, likerId);
            }

            return sqlSession.flushStatements()
                    .get(0)
                    .getUpdateCounts();
        }
    }

    private List<Long> filterValidLikerId(final List<Long> likerIdList, final int[] updateCounts) {
        return IntStream.range(0, likerIdList.size())
                .filter(i -> updateCounts[i] == 1)
                .mapToObj(likerIdList::get)
                .toList();
    }
}
