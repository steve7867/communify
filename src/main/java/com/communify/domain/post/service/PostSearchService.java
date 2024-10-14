package com.communify.domain.post.service;

import com.communify.domain.post.PostRepository;
import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostSearchService {

    private static final Integer searchSize = 20;

    private final PostRepository postRepository;

    @Cacheable(cacheNames = CacheNames.HOT_POST_OUTLINES, key = "#lastPostId", sync = true)
    public List<PostOutline> getHotPostOutlines(final Long lastPostId) {
        final List<PostOutline> hotPostOutlineList = postRepository.findHotPostOutlines(lastPostId, searchSize);
        return Collections.unmodifiableList(hotPostOutlineList);
    }

    @Cacheable(cacheNames = CacheNames.POST_OUTLINES, key = "#categoryId + '_' + #lastPostId", sync = true)
    public List<PostOutline> getPostOutlinesByCategory(final Long categoryId, final Long lastPostId) {
        final List<PostOutline> postOutlineList = postRepository.findPostOutlinesByCategory(categoryId, lastPostId, searchSize);
        return Collections.unmodifiableList(postOutlineList);
    }

    public List<PostOutline> getPostOutlinesByMember(final Long writerId, final Long lastPostId) {
        final List<PostOutline> postOutlineList = postRepository.findPostOutlinesByMember(writerId, lastPostId, searchSize);
        return Collections.unmodifiableList(postOutlineList);
    }

    @Cacheable(cacheNames = CacheNames.POST_DETAIL, key = "#postId", sync = true)
    public Optional<PostDetail> getPostDetail(final Long postId) {
        return postRepository.findPostDetail(postId);
    }
}
