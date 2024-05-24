package com.communify.domain.post.dao;

import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostSearchCondition;
import com.communify.domain.post.dto.PostUploadRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostRepository {

    void insertPost(@Param("request") PostUploadRequest request,
                    @Param("memberId") Long memberId);

    List<PostOutline> findAllPostOutlineBySearchCond(PostSearchCondition searchCond);
}
