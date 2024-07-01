package com.communify.domain.follow.applilcation;

import com.communify.domain.follow.dao.FollowRepository;
import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.follow.dto.FollowerSearchCondition;
import com.communify.domain.follow.dto.FolloweeSearchCondition;
import com.communify.domain.follow.dto.UnfollowRequest;
import com.communify.domain.member.dao.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void follow(final FollowRequest request) {
        final Integer count = followRepository.insertFollow(request);
        if (count == 0) {
            return;
        }

        memberRepository.incrementFollowerCount(request.getFolloweeId(), count);
        memberRepository.incrementFolloweeCount(request.getFollowerId(), count);

        eventPublisher.publishEvent(new FollowEvent(request));
    }

    @Transactional
    public void unfollow(final UnfollowRequest request) {
        final Integer count = followRepository.deleteFollow(request);
        if (count == 0) {
            return;
        }

        memberRepository.decrementFollowerCount(request.getFolloweeId(), count);
        memberRepository.decrementFolloweeCount(request.getFollowerId(), count);
    }

    @Transactional(readOnly = true)
    public List<MemberInfo> getFollowers(final FollowerSearchCondition searchCond) {
        final List<MemberInfo> followerList = followRepository.findFollowers(searchCond);
        return Collections.unmodifiableList(followerList);
    }

    @Transactional(readOnly = true)
    public List<MemberInfo> getFollowees(final FolloweeSearchCondition searchCond) {
        final List<MemberInfo> followeeList = followRepository.findFollowees(searchCond);
        return Collections.unmodifiableList(followeeList);
    }
}
