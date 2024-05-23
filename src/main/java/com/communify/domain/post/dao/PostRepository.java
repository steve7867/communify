package com.communify.domain.post.dao;

import com.communify.domain.post.dto.PostUploadRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PostRepository {

    void insertPost(@Param("request") PostUploadRequest request,
                    @Param("memberId") Long memberId);
}
