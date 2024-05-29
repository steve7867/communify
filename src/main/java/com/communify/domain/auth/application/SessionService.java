package com.communify.domain.auth.application;

public interface SessionService {

    void add(String key, Object value);

    void invalidate();

    Object get(String key);
}
