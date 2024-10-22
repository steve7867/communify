package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentListContainer;
import com.communify.domain.comment.dto.CommentUploadEvent;
import com.communify.domain.post.PostRepository;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private static final Integer SEARCH_SIZE = 50;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
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
    @Cacheable(cacheNames = CacheNames.COMMENTS,
            key = "#postId + '_' + #lastCommentId",
            unless = "T(java.time.Duration).between(#result.createdDateTime, T(java.time.LocalDateTime).now()).toHours() > 24")
    public CommentListContainer getComments(final Long postId, final Long lastCommentId) {
        return commentRepository.findCommentsByPostId(postId, lastCommentId, SEARCH_SIZE);
    }

    public void editComment(final Long postId,
                            final Long commentId,
                            final String content,
                            final Long requesterId) {

        commentRepository.editComment(postId, commentId, content, requesterId);
    }

    @Transactional
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
