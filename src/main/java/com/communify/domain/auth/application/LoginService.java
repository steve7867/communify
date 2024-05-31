package com.communify.domain.auth.application;

import com.communify.domain.auth.dto.login.LoginRequest;
import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.application.SessionService;
import com.communify.global.util.PasswordEncryptor;
import com.communify.global.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberFindService memberFindService;
    private final SessionService sessionService;

    public void login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        MemberInfo memberInfo = memberFindService.findMemberInfoByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        String hashed = memberInfo.getHashed();
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
