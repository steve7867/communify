package com.communify.domain.member.application;

import com.communify.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository memberRepository;

    public void setFcmToken(String fcmToken, Long memberId) {
        memberRepository.setFcmToken(fcmToken, memberId);
    }
}
