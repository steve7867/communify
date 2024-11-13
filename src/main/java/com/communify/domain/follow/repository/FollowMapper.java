package com.communify.domain.follow.repository;

import com.communify.domain.follow.dto.UserInfoForFollowSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
interface FollowMapper {

    void insertFollow(Long followerId, Long followeeId);

    boolean deleteFollow(Long followerId, Long followeeId);

    List<UserInfoForFollowSearch> findFollowers(Long followeeId, Long lastFollowerId, Long searcherId);

    List<UserInfoForFollowSearch> findFollowees(Long followerId, Long lastFolloweeId, Long searcherId);
}
