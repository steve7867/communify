package com.communify.domain.member.application;

import com.communify.domain.Auth.application.AuthService;
import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.dto.MemberSignUpRequest;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import com.communify.domain.member.error.exception.EmailAlreadyUsedException;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthService authService;

    public void signUp(MemberSignUpRequest request) {
        try {
            memberRepository.insert(request);
        } catch (DuplicateKeyException e) {
            throw new EmailAlreadyUsedException(request.getEmail(), e);
        }
    }

    public MemberInfo findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    public MemberInfo findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    public void withdraw(MemberWithdrawRequest request, Long memberId) {
        String password = request.getPassword();

        authService.certify(password, memberId);

        memberRepository.deleteById(memberId);
        authService.logout();
    }
}
