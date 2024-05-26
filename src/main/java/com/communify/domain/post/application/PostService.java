package com.communify.domain.post.application;

import com.communify.domain.file.application.FileService;
import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostSearchCondition;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.post.error.exception.InvalidPostAccessException;
import com.communify.domain.post.error.exception.PostNotFoundException;
import com.communify.global.application.CacheService;
import com.communify.global.util.CacheKeyUtil;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final CacheService cacheService;

    @Transactional
    public void uploadPost(PostUploadRequest request,
                           Long memberId) {

        postRepository.insertPost(request, memberId);

        List<MultipartFile> multipartFileList = Collections.unmodifiableList(request.getFileList());
        Long postId = request.getId();
        fileService.uploadFile(multipartFileList, postId);
    }

    @Transactional(readOnly = true) //todo:캐싱 적용
    public List<PostOutline> getPostOutlineList(PostSearchCondition searchCond) {
        return postRepository.findAllPostOutlineBySearchCond(searchCond);
    }

    @Transactional //todo: 캐싱 적용
    public PostDetail getPostDetail(Long postId) {
        return postRepository.findPostDetail(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    public void incrementView(Long postId, Long memberId) {
        String key = CacheKeyUtil.makeCacheKey(CacheNames.POST_VIEW, postId);
        cacheService.addToSet(key, memberId);
    }

    @Transactional //todo: 캐싱 적용
    public void editPost(Long postId,
                         PostUploadRequest request,
                         Long memberId) {

        boolean isEdited = postRepository.editPost(postId, request, memberId);
        if (!isEdited) {
            throw new InvalidPostAccessException(postId, memberId);
        }

        List<MultipartFile> multipartFileList = Collections.unmodifiableList(request.getFileList());
        fileService.updateFiles(multipartFileList, postId);
    }

    @Transactional //todo: 캐시 에빅트 적용
    public void deletePost(Long postId, Long memberId) {
        boolean isDeleted = postRepository.deletePost(postId, memberId);
        if (!isDeleted) {
            throw new InvalidPostAccessException(postId, memberId);
        }
    }
}
