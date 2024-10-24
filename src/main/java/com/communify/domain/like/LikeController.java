package com.communify.domain.like;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.like.service.LikeService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    @ResponseStatus(OK)
    @LoginCheck
    public void like(@PathVariable @NotNull @Positive Long postId,
                     @MemberId Long likerId) {

        likeService.like(postId, likerId);
    }

    @GetMapping("/{postId}/isLiking")
    @ResponseStatus(OK)
    @LoginCheck
    public Boolean isLiking(@PathVariable @NotNull @Positive Long postId,
                            @MemberId Long likerId) {

        return likeService.isLiking(postId, likerId);
    }
}
