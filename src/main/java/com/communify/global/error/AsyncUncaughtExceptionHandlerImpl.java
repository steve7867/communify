package com.communify.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class AsyncUncaughtExceptionHandlerImpl implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(final Throwable ex,
                                        final Method method,
                                        final Object... params) {

        log.error(ex.getMessage(), ex);
    }
}
