package com.communify.domain.auth.application;

public interface SessionService {

    void add(String key, Object value);

    Object get(String key);

    void remove(String key);

    void invalidate();
}
