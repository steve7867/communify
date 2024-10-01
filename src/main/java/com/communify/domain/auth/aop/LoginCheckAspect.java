package com.communify.domain.auth.aop;

import com.communify.domain.auth.application.LoginService;
import com.communify.domain.auth.error.exception.AlreadyLoggedInException;
import com.communify.domain.auth.error.exception.NotLoggedInException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final LoginService loginService;

    @Before("@annotation(com.communify.domain.auth.annotation.NotLoginCheck)")
    public void checkNotLoggedIn() {
        final boolean loggedIn = loginService.isLoggedIn();
        if (loggedIn) {
            throw new AlreadyLoggedInException();
        }
    }

    @Before("@annotation(com.communify.domain.auth.annotation.LoginCheck)")
    public void checkLoggedIn() {
        final boolean loggedIn = loginService.isLoggedIn();
        if (!loggedIn) {
            throw new NotLoggedInException();
        }
    }
}
