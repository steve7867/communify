package com.communify.domain.like.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.like.application.LikeService;
import com.communify.domain.like.dto.LikeRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
    public void like(@PathVariable @NotNull @Positive final Long postId,
                     @MemberId final Long likerId) {

        final LikeRequest request = LikeRequest.builder()
                .postId(postId)
                .likerId(likerId)
                .build();

        likeService.like(request);
    }
}
