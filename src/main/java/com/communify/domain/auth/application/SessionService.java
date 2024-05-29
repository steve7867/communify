package com.communify.domain.auth.application;

public interface SessionService {

    void add(String key, Object value);

    void invalidate();

    boolean isLoggedIn();

    Object getValue(String sessionKey);
}
