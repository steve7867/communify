package com.communify.domain.comment.dao;

import com.communify.domain.comment.dto.CommentUploadRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentRepository {

    void insert(CommentUploadRequest request);
}
