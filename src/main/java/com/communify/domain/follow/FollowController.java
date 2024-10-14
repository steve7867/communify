package com.communify.domain.follow;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.follow.dto.MemberInfoForFollowSearch;
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

        followService.follow(followerId, followerName, followeeId);
    }

    @DeleteMapping("/{followeeId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void unfollow(@PathVariable @NotNull @Positive final Long followeeId,
                         @MemberId final Long followerId) {

        followService.unfollow(followerId, followeeId);
    }

    @GetMapping("/{followeeId}/followers")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfoForFollowSearch> getFollowers(@PathVariable @NotNull @Positive final Long followeeId,
                                                        @RequestParam(required = false) @Positive final Long lastFollowerId,
                                                        @MemberId final Long searcherId) {

        return followService.getFollowers(followeeId, lastFollowerId, searcherId);
    }

    @GetMapping("/{followerId}/followees")
    @ResponseStatus(OK)
    @LoginCheck
    public List<MemberInfoForFollowSearch> getFollowees(@PathVariable @NotNull @Positive final Long followerId,
                                                        @RequestParam(required = false) @Positive final Long lastFolloweeId,
                                                        @MemberId final Long searcherId) {

        return followService.getFollowees(followerId, lastFolloweeId, searcherId);
    }
}
