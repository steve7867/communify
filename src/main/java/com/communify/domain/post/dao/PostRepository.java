package com.communify.domain.post.dao;

import com.communify.domain.post.dto.PostOutlineSearchConditionByMember;
import com.communify.domain.post.dto.PostDeleteRequest;
import com.communify.domain.post.dto.PostEditRequest;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.post.dto.PostViewIncrementRequest;
import com.communify.domain.post.dto.PostOutlineSearchConditionByCategory;
import com.communify.domain.post.dto.outgoing.PostDetail;
import com.communify.domain.post.dto.outgoing.PostOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {

    void insertPost(@Param("request") PostUploadRequest request);

    List<PostOutline> findPostOutlineByCategory(@Param("cond") PostOutlineSearchConditionByCategory searchCond);

    List<PostOutline> findPostOutlineByMember(@Param("cond") PostOutlineSearchConditionByMember searchCond);

    Optional<PostDetail> findPostDetail(Long postId, Long memberId);

    void incrementViewCount(List<PostViewIncrementRequest> list);

    void incrementLikeCount(Long postId, Integer likeCount);

    void incrementCommentCount(Long postId, Integer count);

    void decrementCommentCount(Long postId, Integer count);

    boolean editPost(@Param("request") PostEditRequest request);

    boolean deletePost(@Param("request") PostDeleteRequest request);

    boolean isWrittenBy(Long postId, Long memberId);
}
