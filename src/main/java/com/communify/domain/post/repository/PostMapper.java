package com.communify.domain.post.repository;

import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostUploadDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
interface PostMapper {

    void insertPost(@Param("dto") PostUploadDto dto);

    List<PostOutline> findHotPostOutlines(Long postId, Integer searchSize);

    List<PostOutline> findPostOutlinesByCategory(Long categoryId, Long lastPostId, Integer searchSize);

    List<PostOutline> findPostOutlinesByUser(Long writerId, Long lastPostId, Integer searchSize);

    Optional<PostDetail> findPostDetail(Long postId);

    void incViewCount(Long postId, Integer count);

    void incLikeCount(Long postId, Integer count);

    void incCommentCount(Long postId, Integer count);

    void decCommentCount(Long postId, Integer count);

    boolean editPost(Long postId, String title, String content, Long categoryId, Long requesterId);

    boolean deletePost(Long postId, Long requesterId);

    void promoteToHot(Long postId);
}
