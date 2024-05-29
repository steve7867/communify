package com.communify.domain.auth.application;

public interface SessionService {

    void add(String key, Object value);

    void remove(String key);

    void logout();

    boolean isLoggedIn();

    Object getValue(String sessionKey);
}
