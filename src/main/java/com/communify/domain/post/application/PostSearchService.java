package com.communify.domain.post.application;

import com.communify.domain.hotpost.application.HotPostSearchService;
import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.incoming.PostOutlineSearchCondition;
import com.communify.domain.post.dto.outgoing.PostDetail;
import com.communify.domain.post.dto.outgoing.PostOutline;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostSearchService {

    private final HotPostSearchService hotPostSearchService;
    private final PostRepository postRepository;

    @Value("${post.search-size}")
    private Integer postSearchSize;

    @Cacheable(cacheNames = CacheNames.POST_OUTLINES,
            key = "#searchCond.categoryId + '_' + #searchCond.lastPostId",
            condition = "#searchCond.isSearchingByCategory()")
    public List<PostOutline> getPostOutlineList(PostOutlineSearchCondition searchCond) {
        List<PostOutline> postOutlineList = new ArrayList<>(postSearchSize);

        if (searchCond.isSearchingByCategory() && searchCond.isSearchingUpperMost()) {
            Long categoryId = searchCond.getCategoryId();
            List<PostOutline> hotPostOutlineList = hotPostSearchService.getHotPostOutlineListByCategory(categoryId);
            postOutlineList.addAll(hotPostOutlineList);
        }

        Integer limit = postSearchSize - postOutlineList.size();
        postOutlineList.addAll(postRepository.findAllPostOutlineBySearchCond(searchCond, limit));

        return postOutlineList;
    }

    @Cacheable(cacheNames = CacheNames.POST_DETAIL, key = "#postId")
    public Optional<PostDetail> getPostDetail(Long postId, Long memberId) {
        return postRepository.findPostDetail(postId, memberId);
    }

    public Optional<Long> getWriterId(Long postId) {
        return postRepository.findWriterId(postId);
    }
}
