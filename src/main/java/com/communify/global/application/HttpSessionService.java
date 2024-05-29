package com.communify.global.application;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpSessionService implements SessionService {

    private final HttpSession httpSession;

    @Override
    public void add(String key, Object value) {
        httpSession.setAttribute(key, value);
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.ofNullable(httpSession.getAttribute(key));
    }

    @Override
    public void remove(String key) {
        httpSession.removeAttribute(key);
    }

    @Override
    public void invalidate() {
        httpSession.invalidate();
    }
}
