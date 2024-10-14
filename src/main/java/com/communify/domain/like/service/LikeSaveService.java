package com.communify.domain.like.service;

import com.communify.domain.like.LikeRepository;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeSaveService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void savePostLike(final Long postId, final List<Long> likerIdList) {
        final Integer insertedCount = likeRepository.insertLikeBulk(postId, likerIdList);
        postRepository.incLikeCount(postId, insertedCount);

        eventPublisher.publishEvent(new LikeEvent(postId, likerIdList));
    }
}
