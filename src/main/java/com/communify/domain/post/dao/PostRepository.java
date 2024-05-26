package com.communify.domain.post.dao;

import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostSearchCondition;
import com.communify.domain.post.dto.PostUploadRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface PostRepository {

    void insertPost(@Param("request") PostUploadRequest request,
                    @Param("memberId") Long memberId);

    List<PostOutline> findAllPostOutlineBySearchCond(PostSearchCondition searchCond);

    Optional<PostDetail> findPostDetail(Long postId);

    void incrementView(@Param("postId") Long postId,
                       @Param("view") Long view);

    boolean editPost(@Param("postId") Long postId,
                     @Param("request") PostUploadRequest request,
                     @Param("memberId") Long memberId);

    boolean deletePost(Long postId, Long memberId);
}
