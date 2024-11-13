package com.communify.domain.comment.repository;

import com.communify.domain.comment.dto.CommentListContainer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
interface CommentMapper {

    void insert(Long postId, String content, Long writerId);

    CommentListContainer findCommentsByPostId(Long postId, Long lastCommentId, Integer searchSize);

    boolean editComment(Long postId, Long commentId, String content, Long requesterId);

    boolean deleteComment(Long postId, Long commentId, Long requesterId);
}
