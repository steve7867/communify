package com.communify.domain.like.application;

import com.communify.domain.like.dao.LikeRepository;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.domain.post.dao.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeSaveService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void savePostLike(final Long postId, final List<LikeRequest> likeRequestList) {
        final Integer count = likeRepository.insertLikeBulk(likeRequestList);
        postRepository.incrementLikeCount(postId, count);
    }
}
