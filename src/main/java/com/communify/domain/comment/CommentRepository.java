package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentRepository {

    Integer insert(Long postId, String content, Long writerId);

    List<CommentInfo> findAllCommentsByPostId(Long postId);

    void editComment(Long postId, Long commentId, String content, Long requesterId);

    Integer deleteComment(Long postId, Long commentId, Long requesterId);
}
