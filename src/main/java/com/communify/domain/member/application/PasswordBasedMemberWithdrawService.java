package com.communify.domain.member.application;

import com.communify.domain.auth.application.VerificationService;
import com.communify.domain.auth.application.LoginService;
import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordBasedMemberWithdrawService implements MemberWithdrawService {

    private final MemberRepository memberRepository;
    private final VerificationService verificationService;
    private final LoginService loginService;

    @Override
    public void withdraw(MemberWithdrawRequest request, Long memberId) {
        String password = request.getPassword();

        verificationService.verifyPassword(password, memberId);

        memberRepository.deleteById(memberId);
        loginService.logout();
    }
}
