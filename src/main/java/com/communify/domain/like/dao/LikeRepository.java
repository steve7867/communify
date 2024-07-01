package com.communify.domain.like.dao;

import com.communify.domain.like.dto.LikeRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LikeRepository {

    Integer insertLikeBulk(List<LikeRequest> likeRequestList);

    Boolean findLiking(Long postId, Long likerId);
}
