package com.communify.domain.post.application;

import com.communify.domain.file.application.FileService;
import com.communify.domain.file.dto.FileUploadRequest;
import com.communify.domain.hotpost.application.HotPostSearchService;
import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostDeleteRequest;
import com.communify.domain.post.dto.PostEditRequest;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.post.dto.event.PostUploadEvent;
import com.communify.domain.post.dto.incoming.PostOutlineSearchCondition;
import com.communify.domain.post.dto.outgoing.PostDetail;
import com.communify.domain.post.dto.outgoing.PostOutline;
import com.communify.domain.post.error.exception.InvalidPostAccessException;
import com.communify.domain.post.error.exception.PostWriterNotFoundException;
import com.communify.global.application.CacheService;
import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final CacheService cacheService;
    private final HotPostSearchService hotPostSearchService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${post.search-size}")
    private Integer postSearchSize;

    @Transactional
    public void uploadPost(PostUploadRequest request) {
        postRepository.insertPost(request);

        Long postId = request.getId();
        fileService.uploadFile(new FileUploadRequest(postId, request.getFileList()));

        Long memberId = request.getMemberId();
        String memberName = request.getMemberName();
        PostUploadEvent event = new PostUploadEvent(memberId, memberName);
        eventPublisher.publishEvent(event);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.POST_OUTLINES,
            key = "#searchCond.categoryId + '_' + #searchCond.lastPostId")
    public List<PostOutline> getPostOutlineList(PostOutlineSearchCondition searchCond) {
        List<PostOutline> postOutlineList = new ArrayList<>(postSearchSize);

        if (Objects.isNull(searchCond.getLastPostId())) {
            Long categoryId = searchCond.getCategoryId();
            List<PostOutline> hotPostOutlineList = hotPostSearchService.getHotPostOutlineListByCategory(categoryId);
            postOutlineList.addAll(hotPostOutlineList);
        }

        Integer limit = postSearchSize - postOutlineList.size();
        postOutlineList.addAll(postRepository.findAllPostOutlineBySearchCond(searchCond, limit));

        return postOutlineList;
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.POST_DETAIL, key = "#postId")
    public Optional<PostDetail> getPostDetail(Long postId) {
        return postRepository.findPostDetail(postId);
    }

    public void incrementView(Long postId, Long memberId) {
        String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_VIEW, postId);
        cacheService.addToSet(key, memberId);
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.POST_DETAIL, key = "#request.postId")
    public void editPost(PostEditRequest request) {
        boolean isEdited = postRepository.editPost(request);
        if (!isEdited) {
            Long postId = request.getPostId();
            Long memberId = request.getMemberId();
            throw new InvalidPostAccessException(postId, memberId);
        }

        Long postId = request.getPostId();
        fileService.deleteFiles(postId);

        fileService.uploadFile(new FileUploadRequest(postId, request.getFileList()));
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.POST_DETAIL, key = "#request.postId")
    public void deletePost(PostDeleteRequest request) {
        Long postId = request.getPostId();
        Long memberId = request.getMemberId();

        boolean canDelete = postRepository.isWrittenBy(postId, memberId);
        if (!canDelete) {
            throw new InvalidPostAccessException(postId, memberId);
        }

        fileService.deleteFiles(postId);

        postRepository.deletePost(request);
    }

    @Transactional(readOnly = true)
    public Long getWriterId(Long postId) {
        return postRepository.findWriterId(postId)
                .orElseThrow(() -> new PostWriterNotFoundException(postId));
    }
}
