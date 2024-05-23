package com.communify.domain.Auth.application;

import com.communify.domain.member.dto.MemberInfo;
import com.communify.global.util.SessionKey;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HttpSessionService implements SessionService{

    private final HttpSession httpSession;

    @Override
    public void login(MemberInfo memberInfo) {
        httpSession.setAttribute(SessionKey.MEMBER_ID, memberInfo.getId());
        httpSession.setAttribute(SessionKey.MEMBER_NAME, memberInfo.getName());
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }

    @Override
    public boolean isLoggedIn() {
        return httpSession.getAttribute(SessionKey.MEMBER_ID) != null;
    }
}
