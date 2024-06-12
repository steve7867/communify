package com.communify.domain.post.dao;

import com.communify.domain.post.dto.PostDeleteRequest;
import com.communify.domain.post.dto.PostEditRequest;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.post.dto.incoming.PostOutlineSearchCondition;
import com.communify.domain.post.dto.outgoing.PostDetail;
import com.communify.domain.post.dto.outgoing.PostOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {

    void insertPost(@Param("request") PostUploadRequest request);

    List<PostOutline> findAllPostOutlineBySearchCond(@Param("searchCond") PostOutlineSearchCondition searchCond,
                                                     @Param("limit") Integer limit);

    Optional<PostDetail> findPostDetail(Long postId);

    void incrementView(@Param("postId") Long postId,
                       @Param("view") Long view);

    boolean editPost(@Param("request") PostEditRequest request);

    boolean deletePost(@Param("request") PostDeleteRequest request);

    boolean isWrittenBy(Long postId, Long memberId);

    Optional<Long> findWriterId(Long postId);

}
