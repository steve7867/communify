package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentListContainer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepository {

    Integer insert(Long postId, String content, Long writerId);

    CommentListContainer findCommentsByPostId(Long postId, Long lastCommentId, Integer searchSize);

    void editComment(Long postId, Long commentId, String content, Long requesterId);

    Integer deleteComment(Long postId, Long commentId, Long requesterId);
}
