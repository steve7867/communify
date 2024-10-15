package com.communify.domain.certification;

import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.certification.dto.CertificationRequestForm;
import com.communify.domain.certification.dto.CodeIssueRequestForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members/certification")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping("/issue-code")
    @ResponseStatus(OK)
    @NotLoginCheck
    public void issueCode(@RequestBody @Valid final CodeIssueRequestForm form) {
        final String email = form.getEmail();

        certificationService.issueCode(email);
    }

    @PostMapping("/certify-code")
    @ResponseStatus(OK)
    @NotLoginCheck
    public void certifyCode(@RequestBody @Valid final CertificationRequestForm form) {
        final String code = form.getCode();

        certificationService.certifyCode(code);
    }
}
