package com.communify.domain.member.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.member.application.MemberSearchService;
import com.communify.domain.member.application.MemberSignUpService;
import com.communify.domain.member.application.MemberUpdateService;
import com.communify.domain.member.application.MemberWithdrawService;
import com.communify.domain.member.dto.MemberSearchRequest;
import com.communify.domain.member.dto.MemberSignUpRequest;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import com.communify.domain.member.dto.PasswordUpdateRequest;
import com.communify.domain.member.dto.incoming.MemberSignUpForm;
import com.communify.domain.member.dto.incoming.MemberWithdrawForm;
import com.communify.domain.member.dto.incoming.PasswordUpdateForm;
import com.communify.domain.member.dto.outgoing.MemberInfoForSearch;
import com.communify.domain.verification.application.VerificationService;
import com.communify.domain.verification.dto.VerificationConfirmRequest;
import com.communify.domain.verification.error.exception.EmailNotVerifiedException;
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

    private final MemberSignUpService memberSignUpService;
    private final MemberSearchService memberSearchService;
    private final MemberUpdateService memberUpdateService;
    private final MemberWithdrawService memberWithdrawService;
    private final VerificationService verificationService;

    @PostMapping
    @ResponseStatus(CREATED)
    @NotLoginCheck
    public void signUp(@RequestBody @Valid final MemberSignUpForm form) {
        final boolean isEmailVerified = verificationService.isVerified(VerificationConfirmRequest.empty());
        if (!isEmailVerified) {
            throw new EmailNotVerifiedException(form.getEmail());
        }

        final MemberSignUpRequest request =
                new MemberSignUpRequest(form.getEmail(), form.getName(), form.getPassword());

        memberSignUpService.signUp(request);
    }

    @GetMapping("/{memberId}")
    @LoginCheck
    public MemberInfoForSearch getMemberInfo(@PathVariable @NotNull @Positive final Long memberId,
                                             @MemberId final Long searcherId) {

        final MemberSearchRequest request = new MemberSearchRequest(memberId, searcherId);
        return memberSearchService.getMemberInfoForSearchById(request);
    }

    @DeleteMapping("/me")
    @ResponseStatus(OK)
    @LoginCheck
    public void withdraw(@RequestBody @Valid final MemberWithdrawForm form,
                         @MemberId final Long memberId) {

        final MemberWithdrawRequest request = new MemberWithdrawRequest(form.getPassword(), memberId);
        memberWithdrawService.withdraw(request);
    }

    @PatchMapping("/password")
    @ResponseStatus(OK)
    @LoginCheck
    public void updatePassword(@RequestBody @Valid final PasswordUpdateForm form,
                               @MemberId final Long memberId) {

        final PasswordUpdateRequest request = PasswordUpdateRequest.builder()
                .memberId(memberId)
                .currentPassword(form.getCurrentPassword())
                .newPassword(form.getNewPassword())
                .build();

        memberUpdateService.updatePassword(request);
    }

    @PostMapping("/fcmToken")
    @ResponseStatus(OK)
    @LoginCheck
    public void setFcmToken(@RequestBody @NotBlank final String fcmToken,
                            @MemberId final Long memberId) {

        memberUpdateService.setFcmToken(fcmToken, memberId);
    }
}
