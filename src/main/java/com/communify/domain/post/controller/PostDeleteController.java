package com.communify.domain.post.controller;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.post.service.PostDeleteService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostDeleteController {

    private final PostDeleteService postDeleteService;

    @DeleteMapping("/{postId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void delete(@PathVariable @NotNull @Positive final Long postId,
                       @MemberId final Long requesterId) {

        postDeleteService.deletePost(postId, requesterId);
    }
}
