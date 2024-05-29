package com.communify.domain.auth.application;

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
    public Object get(String key) {
        return httpSession.getAttribute(key);
    }

    @Override
    public void invalidate() {
        httpSession.invalidate();
    }
}
