package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentAddEvent;
import com.communify.domain.comment.dto.CommentListContainer;
import com.communify.domain.comment.repository.CommentRepository;
import com.communify.domain.post.repository.PostRepository;
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
    public void addComment(Long postId, String content, Long writerId) {
        commentRepository.insert(postId, content, writerId);
        postRepository.incCommentCount(postId, 1);

        eventPublisher.publishEvent(new CommentAddEvent(postId));
    }

    @Cacheable(cacheNames = CacheNames.COMMENTS,
            key = "#postId + '_' + #lastCommentId",
            unless = "T(java.time.Duration).between(#result.postCreatedDateTime, T(java.time.LocalDateTime).now()).toHours() > 24")
    public CommentListContainer getComments(Long postId, Long lastCommentId) {
        return commentRepository.findCommentsByPostId(postId, lastCommentId, SEARCH_SIZE);
    }

    public void editComment(Long postId, Long commentId, String content, Long requesterId) {
        commentRepository.editComment(postId, commentId, content, requesterId);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Long requesterId) {
        commentRepository.deleteComment(postId, commentId, requesterId);
        postRepository.decCommentCount(postId, 1);
    }
}
