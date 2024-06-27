package com.communify.domain.member.application;

import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.outgoing.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberFindService {

    private final MemberRepository memberRepository;

    public Optional<MemberInfo> findMemberInfoByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<MemberInfo> findMemberInfoById(final Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<String> findFcmTokenById(final Long memberId) {
        return memberRepository.findFcmTokenById(memberId);
    }
}
