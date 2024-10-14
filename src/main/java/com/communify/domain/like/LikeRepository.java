package com.communify.domain.like;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LikeRepository {

    Integer insertLikeBulk(Long postId, List<Long> likerIdList);

    Boolean findLiking(Long postId, Long likerId);
}
