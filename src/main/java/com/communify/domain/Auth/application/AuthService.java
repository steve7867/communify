package com.communify.domain.Auth.application;

import com.communify.domain.Auth.dto.LoginRequest;
import com.communify.domain.member.application.MemberService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.global.util.PassEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final SessionService sessionService;

    public void login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        MemberInfo memberInfo = memberService.findMemberInfoByEmail(email);

        String hashed = memberInfo.getHashed();
        if (!PassEncryptor.isMatch(password, hashed)) {
            throw new IllegalArgumentException(); //todo: 예외 처리
        }

        sessionService.login(memberInfo);
    }

    public void logout() {
        sessionService.logout();
    }

    public boolean isLoggedIn() {
        return sessionService.isLoggedIn();
    }

    public boolean certify(String password, Long memberId) {
        MemberInfo memberInfo = memberService.findMemberInfoById(memberId);
        String hashed = memberInfo.getHashed();
        return PassEncryptor.isMatch(password, hashed);
    }
}
