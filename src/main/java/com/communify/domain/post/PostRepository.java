package com.communify.domain.post;

import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostUploadRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {

    void insertPost(@Param("request") PostUploadRequest request);

    List<PostOutline> findHotPostOutlines(Long postId, Integer searchSize);

    List<PostOutline> findPostOutlinesByCategory(Long categoryId, Long lastPostId, Integer searchSize);

    List<PostOutline> findPostOutlinesByMember(Long writerId, Long lastPostId, Integer searchSize);

    Optional<PostDetail> findPostDetail(Long postId);

    Optional<Long> findWriterId(Long postId);

    Optional<PostOutline> findPostOutlineForHotSwitch(Long postId);

    void incViewCount(Long postId, Integer viewCount);

    void incLikeCount(Long postId, Integer likeCount);

    void incCommentCount(Long postId, Integer count);

    void decCommentCount(Long postId, Integer count);

    boolean editPost(Long postId, String title, String content, Long categoryId, Long requesterId);

    boolean deletePost(Long postId, Long requesterId);

    boolean isWrittenBy(Long postId, Long requesterId);

    void makePostAsHot(Long postId);
}
