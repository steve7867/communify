package com.communify.global.application;

import java.util.Optional;

public interface SessionService {

    void add(String key, Object value);

    Optional<Object> get(String key);

    void remove(String key);

    void invalidate();
}
