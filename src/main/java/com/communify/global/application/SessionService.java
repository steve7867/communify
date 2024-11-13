package com.communify.global.application;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final HttpSession httpSession;

    public void add(String key, Object value) {
        httpSession.setAttribute(key, value);
    }

    public Optional<Object> get(String key) {
        return Optional.ofNullable(httpSession.getAttribute(key));
    }

    public void remove(String key) {
        httpSession.removeAttribute(key);
    }

    public void invalidate() {
        httpSession.invalidate();
    }
}
