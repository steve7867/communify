package com.communify.domain.follow.applilcation;

import com.communify.domain.follow.dao.FollowRepository;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.member.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public void follow(FollowRequest request) {
        Long memberId = request.getMemberId();
        Long followId = request.getFollowId();

        followRepository.insertFollow(memberId, followId);

//        eventPublisher.publishEvent(new FollowEvent(request)); //todo: 이벤트 처리
    }

    public void unfollow(Long memberId, Long followId) {
        followRepository.deleteFollow(memberId, followId);
    }

    @Transactional(readOnly = true)
    public List<MemberInfo> getFollowers(Long memberId) {
        List<MemberInfo> followerList = followRepository.findFollowers(memberId);
        return Collections.unmodifiableList(followerList);
    }
}
