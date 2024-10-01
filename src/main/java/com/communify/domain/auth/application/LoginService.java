package com.communify.domain.auth.application;

import com.communify.domain.auth.dto.login.LoginRequest;
import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberInfoForLogin;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.application.session.SessionService;
import com.communify.global.util.PasswordEncryptor;
import com.communify.global.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final SessionService sessionService;

    public void login(final LoginRequest request) {
        final String email = request.getEmail();
        final String password = request.getPassword();

        final MemberInfoForLogin memberInfo = memberRepository.findMemberInfoForLoginByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        final String hashed = memberInfo.getHashed();
        if (!PasswordEncryptor.isMatch(password, hashed)) {
            throw new InvalidPasswordException(password);
        }

        sessionService.add(SessionKey.MEMBER_ID, memberInfo.getId());
        sessionService.add(SessionKey.MEMBER_NAME, memberInfo.getName());
    }

    public void logout() {
        sessionService.invalidate();
    }

    public boolean isLoggedIn() {
        return sessionService.get(SessionKey.MEMBER_ID).isPresent();
    }
}
