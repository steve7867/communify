package com.communify.domain.member.application;

import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.dto.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signUp(MemberSignUpRequest request) {
        try {
            memberRepository.insert(request);
        } catch (DuplicateKeyException e) {
            throw e; //todo: 예외 전환할 것
        }
    }

    public MemberInfo findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException()); //todo: 예외 전환 처리
    }

    public MemberInfo findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException()); //tooo: 예외 처리
    }
}
