package com.communify.global.application.session;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpSessionService implements SessionService {

    private final HttpSession httpSession;

    @Override
    public void add(final String key, final Object value) {
        httpSession.setAttribute(key, value);
    }

    @Override
    public Optional<Object> get(final String key) {
        return Optional.ofNullable(httpSession.getAttribute(key));
    }

    @Override
    public void remove(final String key) {
        httpSession.removeAttribute(key);
    }

    @Override
    public void invalidate() {
        httpSession.invalidate();
    }
}
