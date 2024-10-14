package com.communify.domain.member;

import com.communify.domain.auth.LoginService;
import com.communify.domain.auth.exception.InvalidPasswordException;
import com.communify.domain.member.dto.MemberInfoForSearch;
import com.communify.domain.member.exception.EmailAlreadyUsedException;
import com.communify.domain.member.exception.MemberNotFoundException;
import com.communify.global.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoginService loginService;

    public void signUp(final String email, final String password, final String name) {
        final String hashed = PasswordEncryptor.encrypt(password);

        try {
            memberRepository.insert(email, hashed, name);
        } catch (DuplicateKeyException e) {
            throw new EmailAlreadyUsedException(email, e);
        }
    }

    @Transactional(readOnly = true)
    public MemberInfoForSearch getMemberInfoForSearchById(final Long memberId, final Long searcherId) {
        return memberRepository.findMemberInfoForSearch(memberId, searcherId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    public void withdraw(final String password, final Long memberId) {
        final String hashed = memberRepository.findHashed(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        if (!PasswordEncryptor.isMatch(password, hashed)) {
            throw new InvalidPasswordException(password);
        }

        loginService.logout();

        memberRepository.decFollowerCountOfFollowees(memberId, 1);
        memberRepository.decFolloweeCountOfFollowers(memberId, 1);
        memberRepository.deleteById(memberId);
    }

    public void updatePassword(final String currentPassword, final String newPassword, final Long memberId) {
        final String hashed = memberRepository.findHashed(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        if (!PasswordEncryptor.isMatch(currentPassword, hashed)) {
            throw new InvalidPasswordException(currentPassword);
        }

        final String newHashed = PasswordEncryptor.encrypt(newPassword);
        memberRepository.updatePassword(newHashed, memberId);
    }

    public void setToken(final String token, final Long memberId) {
        memberRepository.setToken(token, memberId);
    }
}
