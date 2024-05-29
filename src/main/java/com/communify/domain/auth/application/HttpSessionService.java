package com.communify.domain.auth.application;

import com.communify.global.util.SessionKey;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HttpSessionService implements SessionService {

    private final HttpSession httpSession;

    @Override
    public void add(String key, Object value) {
        httpSession.setAttribute(key, value);
    }

    @Override
    public void remove(String key) {
        httpSession.removeAttribute(key);
    }

    @Override
    public void invalidate() {
        httpSession.invalidate();
    }

    @Override
    public boolean isLoggedIn() {
        return httpSession.getAttribute(SessionKey.MEMBER_ID) != null;
    }

    @Override
    public Object getValue(String sessionKey) {
        return httpSession.getAttribute(sessionKey);
    }
}
