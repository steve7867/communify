package com.communify.domain.post.application;

import com.communify.domain.file.application.FileService;
import com.communify.domain.file.dto.FileUploadRequest;
import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostEditRequest;
import com.communify.domain.post.error.exception.InvalidPostAccessException;
import com.communify.global.application.cache.PostViewCacheService;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostEditService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final PostViewCacheService postViewCacheService;

    public void incrementView(final Long postId) {
        postViewCacheService.incrementView(postId);
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.POST_DETAIL, key = "#request.postId")
    public void editPost(final PostEditRequest request) {
        final boolean isEdited = postRepository.editPost(request);
        if (!isEdited) {
            final Long postId = request.getPostId();
            final Long memberId = request.getRequesterId();
            throw new InvalidPostAccessException(postId, memberId);
        }

        final Long postId = request.getPostId();
        fileService.deleteFiles(postId);

        fileService.uploadFile(new FileUploadRequest(postId, request.getFileList()));
    }
}
