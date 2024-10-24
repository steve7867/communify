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

    public void saveLike(Long postId, List<Long> likerIdList) {
        int[] updateCounts = batchInsertLikes(postId, likerIdList);

        Integer sum = Arrays.stream(updateCounts).sum();
        postRepository.incLikeCount(postId, sum);

        List<Long> validLikerIdList = filterValidLikerId(likerIdList, updateCounts);

        eventPublisher.publishEvent(new LikeEvent(postId, validLikerIdList));
    }

    private int[] batchInsertLikes(Long postId, List<Long> likerIdList) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            LikeRepository likeRepository = sqlSession.getMapper(LikeRepository.class);

            for (Long likerId : likerIdList) {
                likeRepository.insertLike(postId, likerId);
            }

            return sqlSession.flushStatements()
                    .get(0)
                    .getUpdateCounts();
        }
    }

    private List<Long> filterValidLikerId(List<Long> likerIdList, int[] updateCounts) {
        return IntStream.range(0, likerIdList.size())
                .filter(i -> updateCounts[i] == 1)
                .mapToObj(likerIdList::get)
                .toList();
    }
}
