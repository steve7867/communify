package com.communify.domain.member.application;

import com.communify.domain.auth.application.AuthService;
import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordBasedMemberWithdrawService implements MemberWithdrawService {

    private final MemberRepository memberRepository;
    private final AuthService authService;

    @Override
    public void withdraw(MemberWithdrawRequest request, Long memberId) {
        String password = request.getPassword();

        authService.certify(password, memberId);

        memberRepository.deleteById(memberId);
        authService.logout();
    }
}
