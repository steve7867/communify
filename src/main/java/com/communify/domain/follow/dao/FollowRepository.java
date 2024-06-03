package com.communify.domain.follow.dao;

import com.communify.domain.member.dto.outgoing.MemberInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowRepository {

    void insertFollow(Long memberId, Long followId);

    void deleteFollow(Long memberId, Long followId);

    List<MemberInfo> findFollowers(Long memberId);

    List<MemberInfo> findFollowings(Long memberId);
}
