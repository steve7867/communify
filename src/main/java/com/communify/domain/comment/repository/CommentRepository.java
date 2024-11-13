package com.communify.domain.comment.repository;

import com.communify.domain.comment.dto.CommentListContainer;
import com.communify.domain.comment.exception.InvalidCommentAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final CommentMapper commentMapper;

    public void insert(Long postId, String content, Long writerId) {
        commentMapper.insert(postId, content, writerId);
    }

    @Transactional(readOnly = true)
    public CommentListContainer findCommentsByPostId(Long postId, Long lastCommentId, Integer searchSize) {
        return commentMapper.findCommentsByPostId(postId, lastCommentId, searchSize);
    }

    public void editComment(Long postId, Long commentId, String content, Long requesterId) {
        boolean isEdited = commentMapper.editComment(postId, commentId, content, requesterId);
        if (!isEdited) {
            throw new InvalidCommentAccessException();
        }
    }

    public void deleteComment(Long postId, Long commentId, Long requesterId) {
        boolean isDeleted = commentMapper.deleteComment(postId, commentId, requesterId);
        if (!isDeleted) {
            throw new InvalidCommentAccessException();
        }
    }
}
