package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentInfo;
import com.communify.domain.comment.dto.CommentUploadEvent;
import com.communify.domain.post.PostRepository;
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
    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @CacheEvict(cacheNames = CacheNames.COMMENTS, key = "#postId")
    public void addComment(final Long postId,
                           final String content,
                           final Long writerId,
                           final String writerName) {

        final Integer addedCount = commentRepository.insert(postId, content, writerId);
        if (addedCount == 0) {
            return;
        }

        postRepository.incCommentCount(postId, addedCount);

        eventPublisher.publishEvent(new CommentUploadEvent(postId, content, writerName));
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.COMMENTS, key = "#postId", sync = true)
    public List<CommentInfo> getComments(final Long postId) {
        final List<CommentInfo> commentInfoList = commentRepository.findAllCommentsByPostId(postId);
        return Collections.unmodifiableList(commentInfoList);
    }

    @CacheEvict(cacheNames = CacheNames.COMMENTS, key = "#postId")
    public void editComment(final Long postId,
                            final Long commentId,
                            final String content,
                            final Long requesterId) {

        commentRepository.editComment(postId, commentId, content, requesterId);
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.COMMENTS, key = "#postId")
    public void deleteComment(final Long postId,
                              final Long commentId,
                              final Long requesterId) {

        final Integer deletedCount = commentRepository.deleteComment(postId, commentId, requesterId);
        if (deletedCount == 0) {
            return;
        }

        postRepository.decCommentCount(postId, deletedCount);
    }
}
