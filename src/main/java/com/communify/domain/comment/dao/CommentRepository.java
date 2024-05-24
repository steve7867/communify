package com.communify.domain.comment.dao;

import com.communify.domain.comment.dto.CommentInfo;
import com.communify.domain.comment.dto.CommentUploadRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentRepository {

    void insert(CommentUploadRequest request);

    List<CommentInfo> findAllCommentsByPostId(Long postId);
}
