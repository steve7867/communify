package com.communify.domain.follow.repository;

import com.communify.domain.follow.dto.UserInfoForFollowSearch;
import com.communify.domain.follow.exception.NotFollowingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private final FollowMapper followMapper;

    public void insertFollow(Long followerId, Long followeeId) {
        followMapper.insertFollow(followerId, followeeId);
    }

    public void deleteFollow(Long followerId, Long followeeId) {
        boolean deleted = followMapper.deleteFollow(followerId, followeeId);
        if (!deleted) {
            throw new NotFollowingException();
        }
    }

    @Transactional(readOnly = true)
    public List<UserInfoForFollowSearch> findFollowers(Long followeeId, Long lastFollowerId, Long searcherId) {
        List<UserInfoForFollowSearch> followerList = followMapper.findFollowers(followeeId, lastFollowerId, searcherId);
        return Collections.unmodifiableList(followerList);
    }

    @Transactional(readOnly = true)
    public List<UserInfoForFollowSearch> findFollowees(Long followerId, Long lastFolloweeId, Long searcherId) {
        List<UserInfoForFollowSearch> followeeList = followMapper.findFollowees(followerId, lastFolloweeId, searcherId);
        return Collections.unmodifiableList(followeeList);
    }
}
