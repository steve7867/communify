package com.communify.domain.hotpost.dao;

import com.communify.domain.post.dto.outgoing.PostOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotPostRepository {

    List<PostOutline> findHotPostOutlinesByCategory(@Param("categoryId") Long categoryId,
                                                    @Param("limit") Integer limit);

    List<Long> findMostViewedPostIdFrom(@Param("hour") Integer hour,
                                        @Param("categoryId") Long categoryId,
                                        @Param("limit") Integer limit);

    void deleteAllFromHotPost();

    void insertAllHotPosts(List<Long> postIdList);

    Boolean isHot(Long postId);
}
