package com.communify.domain.member;

import com.communify.domain.member.dto.MemberWithdrawEvent;
import com.communify.global.util.CacheNames;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberWithdrawEventListener {

    private final MemberRepository memberRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Async
    @EventListener
    public void updateFollowInfoOnWithdrawal(MemberWithdrawEvent event) {
        Long memberId = event.getMemberId();

        memberRepository.decFollowerCountOfFollowees(memberId, 1);
        memberRepository.decFolloweeCountOfFollowers(memberId, 1);
        memberRepository.deleteById(memberId);

        redisTemplate.opsForSet().remove(CacheNames.DELETED_MEMBER, memberId);
    }
}
