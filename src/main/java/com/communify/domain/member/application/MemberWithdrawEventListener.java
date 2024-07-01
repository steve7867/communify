package com.communify.domain.member.application;

import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.event.MemberWithdrawEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberWithdrawEventListener {

    private final MemberRepository memberRepository;

    @Async
    @Transactional
    @EventListener
    public void memberWithdraw(final MemberWithdrawEvent event) {
        final Long memberId = event.getMemberId();

        memberRepository.decrementFollowerCountOfFollowees(memberId, 1);
        memberRepository.decrementFolloweeCountOfFollowers(memberId, 1);

        memberRepository.deleteById(memberId);
    }
}
