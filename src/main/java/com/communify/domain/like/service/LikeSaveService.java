package com.communify.domain.like.service;

import com.communify.domain.like.LikeRepository;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikerInfo;
import com.communify.domain.post.PostRepository;
import com.communify.domain.post.dto.PostOutline;
import com.communify.global.util.HotPostDeterminer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeSaveService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Async
    public void saveLike(final Long postId, final List<LikerInfo> likerInfoList) {
        for (LikerInfo info : likerInfoList) {
            final Long likerId = info.getLikerId();
            final String likerName = info.getLikerName();

            final boolean isInserted = likeRepository.insertLike(postId, likerId);
            if (isInserted) {
                postRepository.incLikeCount(postId, 1);
                eventPublisher.publishEvent(new LikeEvent(postId, likerId, likerName));
            }
        }

        final Optional<PostOutline> postOutlineOpt = postRepository.findPostOutlineForHotSwitch(postId);
        if (postOutlineOpt.isEmpty()) {
            return;
        }

        final PostOutline postOutline = postOutlineOpt.get();
        if (postOutline.getIsHot()) {
            return;
        }

        if (!HotPostDeterminer.isCandidateForHot(postOutline)) {
            return;
        }

        postRepository.makePostAsHot(postId);
    }
}
