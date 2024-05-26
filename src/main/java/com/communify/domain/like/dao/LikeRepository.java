package com.communify.domain.like.dao;

import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.dao.BulkInsertable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Mapper
@Repository
public interface LikeRepository extends BulkInsertable<LikeRequest> {

    void insert(@Param("request") LikeRequest request);

    void bulkInsert(Collection<LikeRequest> likeCollection);

    void deleteLike(@Param("postId") Long postId,
                    @Param("memberId") Long memberId);
}
