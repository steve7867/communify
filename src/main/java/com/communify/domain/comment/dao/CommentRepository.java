package com.communify.domain.comment.dao;

import com.communify.domain.comment.dto.CommentDeleteRequest;
import com.communify.domain.comment.dto.CommentEditRequest;
import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.domain.comment.dto.PostWriterInfoForNotification;
import com.communify.domain.comment.dto.outgoing.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentRepository {

    Integer insert(@Param("request") CommentUploadRequest request);

    List<CommentInfo> findAllCommentsByPostId(Long postId);

    void editComment(@Param("request") CommentEditRequest request);

    Integer deleteComment(@Param("request") CommentDeleteRequest request);

    Optional<PostWriterInfoForNotification> findPostWriterInfoForNotification(Long postId);
}
