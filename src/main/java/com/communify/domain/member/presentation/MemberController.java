package com.communify.domain.member.presentation;

import com.communify.domain.auth.annotation.CurrentMemberId;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.auth.application.AuthService;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.application.MemberSignUpService;
import com.communify.domain.member.application.MemberUpdateService;
import com.communify.domain.member.application.MemberWithdrawService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.dto.MemberSignUpRequest;
import com.communify.domain.member.dto.MemberWithdrawRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

import java.util.HashMap;
import java.util.Map;
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
    private final AuthService authService;

    @PostMapping("/email/verification-request")
    @NotLoginCheck
    public ResponseEntity<Object> requestEmailVerification(@RequestBody @Email @NotBlank String email) {
        Optional<MemberInfo> memberInfoOpt = memberFindService.findMemberInfoByEmail(email);

        if (memberInfoOpt.isPresent()) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "이미 사용 중인 email입니다.");

            return ResponseEntity.badRequest().body(map);
        }

        authService.publishEmailVerificationCode(email);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/verify")
    @NotLoginCheck
    public ResponseEntity<Void> verifyEmail(@RequestBody @Email @NotBlank String code) {
        boolean isVerified = authService.verify(code);

        return !isVerified ? ResponseEntity.badRequest().build() : ResponseEntity.ok().build();

    }

    @PostMapping
    @ResponseStatus(CREATED)
    @NotLoginCheck
    public void signUp(@RequestBody @Valid MemberSignUpRequest request) {
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
    public void withdraw(@RequestBody @Valid MemberWithdrawRequest request,
                         @CurrentMemberId Long memberId) {

        memberWithdrawService.withdraw(request, memberId);
    }

    @PostMapping("/fcmToken")
    @ResponseStatus(OK)
    @LoginCheck
    public void setFcmToken(@RequestBody @NotBlank String fcmToken,
                            @CurrentMemberId Long memberId) {

        memberUpdateService.setFcmToken(fcmToken, memberId);
    }
}
