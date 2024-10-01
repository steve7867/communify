package com.communify.domain.member.application;

import com.communify.domain.auth.application.LoginService;
import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberInfoForWithdraw;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordBasedMemberWithdrawService implements MemberWithdrawService {

    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @Override
    public void withdraw(final MemberWithdrawRequest request) {
        final Long memberId = request.getMemberId();

        final MemberInfoForWithdraw memberInfo = memberRepository.findMemberInfoForWithdrawById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        final String password = request.getPassword();
        final String hashed = memberInfo.getHashed();

        if (!PasswordEncryptor.isMatch(password, hashed)) {
            throw new InvalidPasswordException(password);
        }

        loginService.logout();

        memberRepository.decrementFollowerCountOfFollowees(memberId, 1);
        memberRepository.decrementFolloweeCountOfFollowers(memberId, 1);
        memberRepository.deleteById(memberId);
    }
}
