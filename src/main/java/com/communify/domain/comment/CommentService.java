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
    public void addComment(Long postId,
                           String content,
                           Long writerId,
                           String writerName) {

        Integer addedCount = commentRepository.insert(postId, content, writerId);
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
    public CommentListContainer getComments(Long postId, Long lastCommentId) {
        return commentRepository.findCommentsByPostId(postId, lastCommentId, SEARCH_SIZE);
    }

    public void editComment(Long postId,
                            Long commentId,
                            String content,
                            Long requesterId) {

        commentRepository.editComment(postId, commentId, content, requesterId);
    }

    @Transactional
    public void deleteComment(Long postId,
                              Long commentId,
                              Long requesterId) {

        Integer deletedCount = commentRepository.deleteComment(postId, commentId, requesterId);
        if (deletedCount == 0) {
            return;
        }

        postRepository.decCommentCount(postId, deletedCount);
    }
}
