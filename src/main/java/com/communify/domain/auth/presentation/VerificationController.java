package com.communify.domain.auth.presentation;

import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.auth.application.AuthService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final AuthService authService;

    @PostMapping("/code-publish")
    @ResponseStatus(OK)
    @NotLoginCheck
    public void requestEmailVerification(@RequestBody @Email @NotBlank String email) {
        authService.publishEmailVerificationCode(email);
    }

    @PostMapping("/code")
    @ResponseStatus(OK)
    @NotLoginCheck
    public void verifyEmail(@RequestBody @Email @NotBlank String code) {
        authService.verify(code);
    }
}
