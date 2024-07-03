package com.communify.domain.follow.dao;

import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.follow.dto.FolloweeSearchCondition;
import com.communify.domain.follow.dto.FollowerSearchCondition;
import com.communify.domain.follow.dto.MemberInfoForFollowSearch;
import com.communify.domain.follow.dto.UnfollowRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowRepository {

    Integer insertFollow(@Param("request") FollowRequest request);

    Integer deleteFollow(@Param("request") UnfollowRequest request);

    List<MemberInfoForFollowSearch> findFollowers(@Param("cond") FollowerSearchCondition cond);

    List<MemberInfoForFollowSearch> findFollowees(@Param("cond") FolloweeSearchCondition cond);
}
