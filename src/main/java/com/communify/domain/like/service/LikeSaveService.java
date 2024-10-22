package com.communify.domain.like.service;

import com.communify.domain.like.LikeRepository;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikerInfo;
import com.communify.domain.post.PostRepository;
import com.communify.domain.post.dto.PostOutline;
import com.communify.global.util.HotPostDeterminer;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LikeSaveService {

    private final PostRepository postRepository;

    private final ApplicationEventPublisher eventPublisher;
    private final SqlSessionFactory sqlSessionFactory;

    @Async
    @Transactional
    public void saveLike(final Long postId, final List<LikerInfo> likerInfoList) {
        final int[] updateCounts = saveInDB(postId, likerInfoList);

        final int sum = Arrays.stream(updateCounts).sum();
        postRepository.incLikeCount(postId, sum);

        final List<LikerInfo> validLikerInfoList = filterValidLikerInfo(likerInfoList, updateCounts);
        promoteToHotIfEligible(postId);

        eventPublisher.publishEvent(new LikeEvent(postId, validLikerInfoList));
    }

    private int[] saveInDB(Long postId, List<LikerInfo> likerInfoList) {
        try (final SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            final LikeRepository likeRepository = sqlSession.getMapper(LikeRepository.class);

            for (LikerInfo info : likerInfoList) {
                likeRepository.insertLike(postId, info.getLikerId());
            }

            return sqlSession.flushStatements()
                    .get(0)
                    .getUpdateCounts();
        }
    }

    private List<LikerInfo> filterValidLikerInfo(final List<LikerInfo> likerInfoList, final int[] updateCounts) {
        return IntStream.range(0, likerInfoList.size())
                .filter(i -> updateCounts[i] == 1)
                .mapToObj(likerInfoList::get)
                .toList();
    }

    private void promoteToHotIfEligible(final Long postId) {
        final Optional<PostOutline> postOutlineOpt = postRepository.findPostOutlineForHotSwitch(postId);
        if (postOutlineOpt.isEmpty()) {
            return;
        }

        final PostOutline postOutline = postOutlineOpt.get();
        if (postOutline.getIsHot() || !HotPostDeterminer.isCandidateForHot(postOutline)) {
            return;
        }

        postRepository.makePostAsHot(postId);
    }
}
