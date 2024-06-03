package com.communify.domain.member.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.application.MemberSignUpService;
import com.communify.domain.member.application.MemberUpdateService;
import com.communify.domain.member.application.MemberWithdrawService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.dto.MemberSignUpRequest;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import com.communify.domain.member.dto.incoming.PasswordForm;
import com.communify.domain.verification.application.VerificationService;
import com.communify.domain.verification.dto.VerificationConfirmRequest;
import com.communify.domain.verification.error.exception.EmailNotVerifiedException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSignUpService memberSignUpService;
    private final MemberFindService memberFindService;
    private final MemberUpdateService memberUpdateService;
    private final MemberWithdrawService memberWithdrawService;
    private final VerificationService verificationService;

    @PostMapping
    @ResponseStatus(CREATED)
    @NotLoginCheck
    public void signUp(@RequestBody @Valid MemberSignUpRequest request) {
        boolean isEmailVerified = verificationService.isVerified(VerificationConfirmRequest.empty());
        if (!isEmailVerified) {
            throw new EmailNotVerifiedException(request.getEmail());
        }

        memberSignUpService.signUp(request);
    }

    @GetMapping("/{memberId}")
    @LoginCheck
    public ResponseEntity<MemberInfo> getMemberInfo(@PathVariable @NotNull @Positive Long memberId) {
        Optional<MemberInfo> memberInfoOpt = memberFindService.findMemberInfoById(memberId);

        if (memberInfoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(memberInfoOpt.get());
    }

    @DeleteMapping("/me")
    @ResponseStatus(OK)
    @LoginCheck
    public void withdraw(@RequestBody @Valid PasswordForm form,
                         @MemberId Long memberId) {

        MemberWithdrawRequest request = new MemberWithdrawRequest(form.getPassword(), memberId);

        memberWithdrawService.withdraw(request);
    }

    @PostMapping("/fcmToken")
    @ResponseStatus(OK)
    @LoginCheck
    public void setFcmToken(@RequestBody @NotBlank String fcmToken,
                            @MemberId Long memberId) {

        memberUpdateService.setFcmToken(fcmToken, memberId);
    }
}
