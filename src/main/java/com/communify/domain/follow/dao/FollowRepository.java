package com.communify.domain.follow.dao;

import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.follow.dto.UnfollowRequest;
import com.communify.domain.member.dto.outgoing.MemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowRepository {

    void insertFollow(@Param("request") FollowRequest request);

    void deleteFollow(@Param("request") UnfollowRequest request);

    List<MemberInfo> findFollowers(Long memberId);

    List<MemberInfo> findFollowings(Long memberId);
}
