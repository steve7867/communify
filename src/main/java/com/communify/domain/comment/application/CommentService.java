package com.communify.domain.comment.application;

import com.communify.domain.comment.dao.CommentRepository;
import com.communify.domain.comment.dto.CommentDeleteRequest;
import com.communify.domain.comment.dto.CommentEditRequest;
import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.domain.comment.dto.event.CommentUploadEvent;
import com.communify.domain.comment.dto.outgoing.CommentInfo;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @CacheEvict(cacheNames = CacheNames.COMMENTS, key = "#request.postId")
    public void addComment(final CommentUploadRequest request) {
        commentRepository.insert(request);

        eventPublisher.publishEvent(new CommentUploadEvent(request));
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.COMMENTS, key = "#postId", sync = true)
    public List<CommentInfo> getComments(final Long postId) {
        final List<CommentInfo> commentInfoList = commentRepository.findAllCommentsByPostId(postId);
        return Collections.unmodifiableList(commentInfoList);
    }

    @CacheEvict(cacheNames = CacheNames.COMMENTS, key = "#request.postId")
    public void editComment(final CommentEditRequest request) {
        commentRepository.editComment(request);
    }

    @CacheEvict(cacheNames = CacheNames.COMMENTS, key = "#request.postId")
    public void deleteComment(final CommentDeleteRequest request) {
        commentRepository.deleteComment(request);
    }
}
