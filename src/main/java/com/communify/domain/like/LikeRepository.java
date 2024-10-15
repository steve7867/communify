package com.communify.domain.like;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LikeRepository {

    boolean insertLike(Long postId, Long likerId);

    Boolean findLiking(Long postId, Long likerId);
}
