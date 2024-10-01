package com.communify.domain.follow.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.follow.applilcation.FollowService;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.follow.dto.FolloweeSearchCondition;
import com.communify.domain.follow.dto.FollowerSearchCondition;
import com.communify.domain.follow.dto.UnfollowRequest;
import com.communify.domain.follow.dto.outgoing.MemberInfoForFollowSearch;
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

    @PostMapping("/{followeeId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void follow(@PathVariable @NotNull @Positive final Long followeeId,
                       @MemberId final Long followerId,
                       @MemberName final String followerName) {

        final FollowRequest request = new FollowRequest(followerId, followerName, followeeId);
        followService.follow(request);
    }

    @DeleteMapping("/{followeeId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void unfollow(@PathVariable @NotNull @Positive final Long followeeId,
                         @MemberId final Long followerId) {

        final UnfollowRequest request = new UnfollowRequest(followerId, followeeId);
        followService.unfollow(request);
    }

    @GetMapping("/{followeeId}/followers")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfoForFollowSearch> getFollowers(@PathVariable @NotNull @Positive final Long followeeId,
                                                        @RequestParam(required = false) @Positive final Long lastFollowerId,
                                                        @MemberId final Long searcherId) {

        final FollowerSearchCondition searchCond = FollowerSearchCondition.builder()
                .followeeId(followeeId)
                .lastFollowerId(lastFollowerId)
                .searcherId(searcherId)
                .build();

        return followService.getFollowers(searchCond);
    }

    @GetMapping("/{followerId}/followees")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfoForFollowSearch> getFollowees(@PathVariable @NotNull @Positive final Long followerId,
                                                        @RequestParam(required = false) @Positive final Long lastFolloweeId,
                                                        @MemberId final Long searcherId) {

        final FolloweeSearchCondition searchCond = FolloweeSearchCondition.builder()
                .followerId(followerId)
                .lastFolloweeId(lastFolloweeId)
                .searcherId(searcherId)
                .build();

        return followService.getFollowees(searchCond);
    }
}
