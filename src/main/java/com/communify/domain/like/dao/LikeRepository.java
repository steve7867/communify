package com.communify.domain.like.dao;

import com.communify.domain.like.dto.LikeRequest;
import com.communify.global.dao.BulkInsertable;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface LikeRepository extends BulkInsertable<LikeRequest> {

    void insert(@Param("request") LikeRequest request);

    void bulkInsert(Collection<LikeRequest> likeCollection);
}
