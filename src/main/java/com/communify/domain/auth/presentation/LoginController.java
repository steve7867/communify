package com.communify.domain.auth.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.auth.application.LoginService;
import com.communify.domain.auth.dto.login.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @ResponseStatus(OK)
    @NotLoginCheck
    public void login(@RequestBody @Valid LoginRequest request) {
        loginService.login(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(OK)
    @LoginCheck
    public void logout() {
        loginService.logout();
    }
}
