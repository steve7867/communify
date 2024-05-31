package com.communify.domain.comment.dao;

import com.communify.domain.comment.dto.CommentInfo;
import com.communify.domain.comment.dto.CommentUploadRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentRepository {

    void insert(@Param("request") CommentUploadRequest request);

    List<CommentInfo> findAllCommentsByPostId(Long postId);

    void editComment(Long commentId, String content, Long memberId);

    void deleteComment(Long commentId, Long memberId);
}
