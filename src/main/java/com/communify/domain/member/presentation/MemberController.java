package com.communify.domain.member.presentation;

import com.communify.domain.Auth.annotation.NotLoginCheck;
import com.communify.domain.member.application.MemberService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.dto.MemberSignUpRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @NotLoginCheck
    public void signUp(@RequestBody @Valid MemberSignUpRequest request) {
        memberService.signUp(request);
    }

    @GetMapping("/{memberId}")
    @ResponseStatus(OK)
    public MemberInfo getMemberInfo(@PathVariable @NotNull @Positive Long memberId) {
        return memberService.findMemberInfoById(memberId);
    }
}
