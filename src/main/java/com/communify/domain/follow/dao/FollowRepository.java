package com.communify.domain.follow.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowRepository {

    void insertFollow(Long memberId, Long followId);

    void deleteFollow(Long memberId, Long followId);
}
