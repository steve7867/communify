package com.communify.domain.hotpost.dao;

import com.communify.domain.hotpost.dto.AllHotPostSearchCondition;
import com.communify.domain.hotpost.dto.HotPostSearchConditionByCategory;
import com.communify.domain.post.dto.outgoing.PostOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotPostRepository {

    List<PostOutline> findAllHotPostOutlineList(@Param("cond") AllHotPostSearchCondition searchCond);

    List<PostOutline> findHotPostOutlineByCategoryList(@Param("cond") HotPostSearchConditionByCategory searchCond);
}
