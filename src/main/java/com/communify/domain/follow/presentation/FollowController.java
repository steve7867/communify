package com.communify.domain.follow.presentation;

import com.communify.domain.auth.annotation.CurrentMemberId;
import com.communify.domain.auth.annotation.CurrentMemberName;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.follow.applilcation.FollowService;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.follow.error.exception.SelfFollowException;
import com.communify.domain.member.dto.MemberInfo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void follow(@CurrentMemberId Long memberId,
                       @CurrentMemberName String memberName,
                       @PathVariable @NotNull Long followId) {

        if (Objects.equals(memberId, followId)) {
            throw new SelfFollowException(memberId);
        }

        FollowRequest followRequest = new FollowRequest(memberId, memberName, followId);
        followService.follow(followRequest);
    }

    @DeleteMapping("/{followId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void unfollow(@CurrentMemberId Long memberId,
                         @PathVariable @NotNull Long followId) {

        followService.unfollow(memberId, followId);
    }

    @GetMapping("/{memberId}/followers")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfo> getFollowers(@PathVariable Long memberId) {
        return followService.getFollowers(memberId);
    }

    @GetMapping("/{memberId}/followings")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfo> getFollowings(@PathVariable Long memberId) {
        return followService.getFollowings(memberId);
    }
}
