package com.communify.domain.follow;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.follow.dto.MemberInfoForFollowSearch;
import com.communify.domain.member.MemberRepository;
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
    public void follow(Long followerId,
                       String followerName,
                       Long followeeId) {

        Integer count = followRepository.insertFollow(followerId, followeeId);
        if (count == 0) {
            return;
        }

        memberRepository.incFollowerCount(followeeId, count);
        memberRepository.incFolloweeCount(followerId, count);

        eventPublisher.publishEvent(new FollowEvent(followeeId, followerName));
    }

    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        Integer deletedCount = followRepository.deleteFollow(followerId, followeeId);
        if (deletedCount == 0) {
            return;
        }

        memberRepository.decFollowerCount(followeeId, deletedCount);
        memberRepository.decFolloweeCount(followerId, deletedCount);
    }

    @Transactional(readOnly = true)
    public List<MemberInfoForFollowSearch> getFollowers(Long followeeId,
                                                        Long lastFollowerId,
                                                        Long searcherId) {

        List<MemberInfoForFollowSearch> followerList = followRepository.findFollowers(followeeId, lastFollowerId, searcherId);
        return Collections.unmodifiableList(followerList);
    }

    @Transactional(readOnly = true)
    public List<MemberInfoForFollowSearch> getFollowees(Long followerId,
                                                        Long lastFolloweeId,
                                                        Long searcherId) {

        List<MemberInfoForFollowSearch> followeeList = followRepository.findFollowees(followerId, lastFolloweeId, searcherId);
        return Collections.unmodifiableList(followeeList);
    }
}
