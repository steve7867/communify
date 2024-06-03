package com.communify.domain.post.application;

import com.communify.domain.file.application.FileService;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final CacheService cacheService;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void uploadPost(PostUploadRequest request) {
        postRepository.insertPost(request);

        List<MultipartFile> multipartFileList = Collections.unmodifiableList(request.getFileList());
        Long postId = request.getId();
        fileService.uploadFile(multipartFileList, postId);

        Long memberId = request.getMemberId();
        String memberName = request.getMemberName();
        PostUploadEvent event = new PostUploadEvent(memberId, memberName);
        eventPublisher.publishEvent(event);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.POST_OUTLINES,
            key = "#searchCond.categoryId + '_' + #searchCond.lastPostId")
    public List<PostOutline> getPostOutlineList(PostOutlineSearchCondition searchCond) {
        return postRepository.findAllPostOutlineBySearchCond(searchCond);
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
    @CacheEvict(cacheNames = CacheNames.POST_DETAIL, key = "#postId")
    public void editPost(PostEditRequest request) {
        boolean isEdited = postRepository.editPost(request);
        if (!isEdited) {
            Long postId = request.getPostId();
            Long memberId = request.getMemberId();
            throw new InvalidPostAccessException(postId, memberId);
        }

        Long postId = request.getPostId();
        fileService.deleteFiles(postId);

        List<MultipartFile> multipartFileList = Collections.unmodifiableList(request.getFileList());
        fileService.uploadFile(multipartFileList, postId);
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.POST_DETAIL, key = "#postId")
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
