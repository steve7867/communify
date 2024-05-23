package com.communify.domain.Auth.aop;

import com.communify.domain.Auth.application.AuthService;
import com.communify.domain.Auth.error.exception.AlreadyLoggedInException;
import com.communify.domain.Auth.error.exception.NotLoggedInException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizationAspect {

    private final AuthService authService;

    @Before("@annotation(com.communify.domain.Auth.annotation.NotLoginCheck)")
    public void checkNotLoggedIn() {
        boolean loggedIn = authService.isLoggedIn();

        if (loggedIn) {
            throw new AlreadyLoggedInException();
        }
    }

    @Before("@annotation(com.communify.domain.Auth.annotation.LoginCheck)")
    public void checkLoggedIn() {
        boolean loggedIn = authService.isLoggedIn();

        if (!loggedIn) {
            throw new NotLoggedInException();
        }
    }
}
