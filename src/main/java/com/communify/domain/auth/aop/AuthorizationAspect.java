package com.communify.domain.auth.aop;

import com.communify.domain.auth.application.AuthService;
import com.communify.domain.auth.error.exception.AlreadyLoggedInException;
import com.communify.domain.auth.error.exception.NotLoggedInException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizationAspect {

    private final AuthService authService;

    @Before("@annotation(com.communify.domain.auth.annotation.NotLoginCheck)")
    public void checkNotLoggedIn() {
        boolean loggedIn = authService.isLoggedIn();

        if (loggedIn) {
            throw new AlreadyLoggedInException();
        }
    }

    @Before("@annotation(com.communify.domain.auth.annotation.LoginCheck)")
    public void checkLoggedIn() {
        boolean loggedIn = authService.isLoggedIn();

        if (!loggedIn) {
            throw new NotLoggedInException();
        }
    }
}
