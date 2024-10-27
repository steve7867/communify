package com.communify.domain.member;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.certification.CertificationService;
import com.communify.domain.certification.exception.EmailNotCertifiedException;
import com.communify.domain.member.dto.MemberInfoForSearch;
import com.communify.domain.member.dto.MemberSignUpForm;
import com.communify.domain.member.dto.MemberWithdrawForm;
import com.communify.domain.member.dto.PasswordUpdateForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CertificationService certificationService;

    @PostMapping
    @ResponseStatus(CREATED)
    @NotLoginCheck
    public void signUp(@RequestBody @Valid MemberSignUpForm form) {
        boolean isEmailCertified = certificationService.isCertified();
        if (!isEmailCertified) {
            throw new EmailNotCertifiedException(form.getEmail());
        }

        String email = form.getEmail();
        String password = form.getPassword();
        String name = form.getName();

        memberService.signUp(email, password, name);
    }

    @GetMapping("/{memberId}")
    @LoginCheck
    public MemberInfoForSearch getMemberInfo(@PathVariable @NotNull @Positive Long memberId,
                                             @MemberId Long searcherId) {

        return memberService.getMemberInfoForSearchById(memberId, searcherId);
    }

    @DeleteMapping("/me")
    @ResponseStatus(OK)
    @LoginCheck
    public void withdraw(@RequestBody @Valid MemberWithdrawForm form,
                         @MemberId Long memberId) {

        String password = form.getPassword();
        memberService.withdraw(password, memberId);
    }

    @PatchMapping("/me/password")
    @ResponseStatus(OK)
    @LoginCheck
    public void updatePassword(@RequestBody @Valid PasswordUpdateForm form,
                               @MemberId Long memberId) {

        String currentPassword = form.getCurrentPassword();
        String newPassword = form.getNewPassword();

        memberService.updatePassword(currentPassword, newPassword, memberId);
    }

    @PostMapping("/me/token")
    @ResponseStatus(OK)
    @LoginCheck
    public void setToken(@RequestBody @NotBlank String token,
                         @MemberId Long memberId) {

        memberService.setToken(token, memberId);
    }
}
