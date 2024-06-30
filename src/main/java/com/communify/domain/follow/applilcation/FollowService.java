package com.communify.domain.follow.applilcation;

import com.communify.domain.follow.dao.FollowRepository;
import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.follow.dto.UnfollowRequest;
import com.communify.domain.member.dto.outgoing.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void follow(final FollowRequest request) {
        followRepository.insertFollow(request);

        eventPublisher.publishEvent(new FollowEvent(request));
    }

    public void unfollow(final UnfollowRequest request) {
        followRepository.deleteFollow(request);
    }

    @Transactional(readOnly = true)
    public List<MemberInfo> getFollowers(final Long followedId) {
        final List<MemberInfo> followerList = followRepository.findFollowers(followedId);
        return Collections.unmodifiableList(followerList);
    }

    @Transactional(readOnly = true)
    public List<MemberInfo> getFollowings(final Long followerId) {
        final List<MemberInfo> followingList = followRepository.findFollowings(followerId);
        return Collections.unmodifiableList(followingList);
    }
}
