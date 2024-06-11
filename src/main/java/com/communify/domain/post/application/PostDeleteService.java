package com.communify.domain.post.application;

import com.communify.domain.file.application.FileService;
import com.communify.domain.post.dao.PostRepository;
import com.communify.domain.post.dto.PostDeleteRequest;
import com.communify.domain.post.error.exception.InvalidPostAccessException;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostDeleteService {

    private final PostRepository postRepository;
    private final FileService fileService;

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
}
