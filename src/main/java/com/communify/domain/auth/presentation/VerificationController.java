package com.communify.domain.auth.presentation;

import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.auth.application.VerificationService;
import com.communify.domain.auth.dto.CodeIssueRequest;
import jakarta.validation.Valid;
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

    private final VerificationService verificationService;

    @PostMapping("/code-issue")
    @ResponseStatus(OK)
    @NotLoginCheck
    public void requestVerificationCodeIssue(@RequestBody @Valid CodeIssueRequest request) {
        verificationService.issueVerificationCode(request);
    }

    @PostMapping("/code")
    @ResponseStatus(OK)
    @NotLoginCheck
    public void verifyCode(@RequestBody @Email @NotBlank String code) {
        verificationService.verifyCode(code);
    }
}
