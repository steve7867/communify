package com.communify.domain.like.presentation;

import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.like.application.LikeService;
import com.communify.domain.like.dto.LikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public void like(@PathVariable Long postId,
                     @MemberId Long memberId,
                     @MemberName String memberName) {

        LikeRequest request = LikeRequest.builder()
                .postId(postId)
                .memberId(memberId)
                .memberName(memberName)
                .build();

        likeService.like(request);
    }

    @DeleteMapping("/{postId}/like")
    @ResponseStatus(OK)
    @LoginCheck
    public void cancelLike(@PathVariable Long postId,
                           @MemberId Long memberId) {

        likeService.cancelLike(postId, memberId);
    }
}
