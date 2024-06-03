package com.communify.domain.comment.application;

import com.communify.domain.comment.dao.CommentRepository;
import com.communify.domain.comment.dto.CommentEditRequest;
import com.communify.domain.comment.dto.CommentInfo;
import com.communify.domain.comment.dto.CommentUploadEvent;
import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
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

    public void addComment(CommentUploadRequest request) {
        commentRepository.insert(request);

        eventPublisher.publishEvent(new CommentUploadEvent(request));
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.COMMENTS, key = "#postId")
    public List<CommentInfo> getComments(Long postId) {
        List<CommentInfo> commentInfoList = commentRepository.findAllCommentsByPostId(postId);
        return Collections.unmodifiableList(commentInfoList);
    }

    public void editComment(CommentEditRequest request) {
        commentRepository.editComment(request);
    }

    public void deleteComment(Long commentId, Long memberId) {
        commentRepository.deleteComment(commentId, memberId);
    }
}
