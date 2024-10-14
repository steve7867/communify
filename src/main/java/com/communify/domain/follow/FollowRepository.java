package com.communify.domain.follow;

import com.communify.domain.follow.dto.MemberInfoForFollowSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowRepository {

    Integer insertFollow(Long followerId, Long followeeId);

    Integer deleteFollow(Long followerId, Long followeeId);

    List<MemberInfoForFollowSearch> findFollowers(Long followeeId, Long lastFollowerId, Long searcherId);

    List<MemberInfoForFollowSearch> findFollowees(Long followerId, Long lastFolloweeId, Long searcherId);
}
