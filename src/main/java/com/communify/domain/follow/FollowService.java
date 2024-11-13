package com.communify.domain.follow;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.follow.dto.UserInfoForFollowSearch;
import com.communify.domain.follow.repository.FollowRepository;
import com.communify.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void follow(Long followerId, String followerName, Long followeeId) {
        followRepository.insertFollow(followerId, followeeId);

        userRepository.incFollowerCount(followeeId, 1);
        userRepository.incFolloweeCount(followerId, 1);

        eventPublisher.publishEvent(new FollowEvent(followeeId, followerName));
    }

    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        followRepository.deleteFollow(followerId, followeeId);

        userRepository.decFollowerCount(followeeId, 1);
        userRepository.decFolloweeCount(followerId, 1);
    }

    public List<UserInfoForFollowSearch> getFollowers(Long followeeId, Long lastFollowerId, Long searcherId) {
        return followRepository.findFollowers(followeeId, lastFollowerId, searcherId);
    }

    public List<UserInfoForFollowSearch> getFollowees(Long followerId, Long lastFolloweeId, Long searcherId) {
        return followRepository.findFollowees(followerId, lastFolloweeId, searcherId);
    }
}
