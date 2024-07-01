package com.communify.domain.follow.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.follow.applilcation.FollowService;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.follow.dto.FollowerSearchCondition;
import com.communify.domain.follow.dto.FollowingSearchCondition;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followedId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void follow(@PathVariable @NotNull @Positive final Long followedId,
                       @MemberId final Long followerId,
                       @MemberName final String followerName) {

        final FollowRequest request = new FollowRequest(followerId, followerName, followedId);
        followService.follow(request);
    }

    @DeleteMapping("/{followedId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void unfollow(@PathVariable @NotNull @Positive final Long followedId,
                         @MemberId final Long followerId) {

        final UnfollowRequest request = new UnfollowRequest(followerId, followedId);
        followService.unfollow(request);
    }

    @GetMapping("/{followedId}/followers")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfo> getFollowers(@PathVariable @NotNull @Positive final Long followedId,
                                         @RequestParam @Positive final Long lastFollowerId,
                                         @MemberId final Long searcherId) {

        final FollowerSearchCondition searchCondition = FollowerSearchCondition.builder()
                .followedId(followedId)
                .lastFollowerId(lastFollowerId)
                .searcherId(searcherId)
                .build();

        return followService.getFollowers(searchCondition);
    }

    @GetMapping("/{followerId}/followings")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfo> getFollowings(@PathVariable @NotNull @Positive final Long followerId,
                                          @RequestParam @Positive final Long lastFollowingId,
                                          @MemberId final Long searcherId) {

        final FollowingSearchCondition searchCondition = FollowingSearchCondition.builder()
                .followerId(followerId)
                .lastFollowingId(lastFollowingId)
                .searcherId(searcherId)
                .build();

        return followService.getFollowings(searchCondition);
    }
}
