package com.communify.domain.follow.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.follow.applilcation.FollowService;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.follow.dto.UnfollowRequest;
import com.communify.domain.member.dto.outgoing.MemberInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void follow(@PathVariable @NotNull @Positive final Long followId,
                       @MemberId final Long memberId,
                       @MemberName final String memberName) {

        final FollowRequest request = new FollowRequest(memberId, memberName, followId);
        followService.follow(request);
    }

    @DeleteMapping("/{followId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void unfollow(@PathVariable @NotNull @Positive final Long followId,
                         @MemberId final Long memberId) {

        final UnfollowRequest request = new UnfollowRequest(memberId, followId);
        followService.unfollow(request);
    }

    @GetMapping("/{memberId}/followers")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfo> getFollowers(@PathVariable @NotNull @Positive final Long memberId) {
        return followService.getFollowers(memberId);
    }

    @GetMapping("/{memberId}/followings")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfo> getFollowings(@PathVariable @NotNull @Positive final Long memberId) {
        return followService.getFollowings(memberId);
    }
}
