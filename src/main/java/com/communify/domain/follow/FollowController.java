package com.communify.domain.follow;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.UserId;
import com.communify.domain.auth.annotation.UserName;
import com.communify.domain.follow.dto.UserInfoForFollowSearch;
import com.communify.domain.follow.exception.SelfFollowException;
import com.communify.domain.follow.exception.SelfUnfollowException;
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
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followeeId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void follow(@PathVariable @NotNull @Positive Long followeeId,
                       @UserId Long followerId,
                       @UserName String followerName) {

        if (Objects.equals(followerId, followeeId)) {
            throw new SelfFollowException();
        }

        followService.follow(followerId, followerName, followeeId);
    }

    @DeleteMapping("/{followeeId}/follow")
    @ResponseStatus(OK)
    @LoginCheck
    public void unfollow(@PathVariable @NotNull @Positive Long followeeId,
                         @UserId Long followerId) {

        if (Objects.equals(followerId, followeeId)) {
            throw new SelfUnfollowException();
        }

        followService.unfollow(followerId, followeeId);
    }

    @GetMapping("/{followeeId}/followers")
    @ResponseStatus(OK)
    @LoginCheck
    public List<UserInfoForFollowSearch> getFollowers(@PathVariable @NotNull @Positive Long followeeId,
                                                      @RequestParam(required = false) @Positive Long lastFollowerId,
                                                      @UserId Long searcherId) {

        return followService.getFollowers(followeeId, lastFollowerId, searcherId);
    }

    @GetMapping("/{followerId}/followees")
    @ResponseStatus(OK)
    @LoginCheck
    public List<UserInfoForFollowSearch> getFollowees(@PathVariable @NotNull @Positive Long followerId,
                                                      @RequestParam(required = false) @Positive Long lastFolloweeId,
                                                      @UserId Long searcherId) {

        return followService.getFollowees(followerId, lastFolloweeId, searcherId);
    }
}
