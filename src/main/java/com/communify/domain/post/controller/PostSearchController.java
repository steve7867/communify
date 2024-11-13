package com.communify.domain.post.controller;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.service.PostSearchService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;

    @GetMapping("/posts/hot")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getHotPostOutlines(@RequestParam(required = false) @Positive Long lastPostId) {
        return postSearchService.getHotPostOutlines(lastPostId);
    }

    @GetMapping("/categories/{categoryId}/posts")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPostOutlinesByCategory(@PathVariable @Positive @NotNull Long categoryId,
                                                       @RequestParam(required = false) @Positive Long lastPostId) {

        return postSearchService.getPostOutlinesByCategory(categoryId, lastPostId);
    }

    @GetMapping("/users/{userId}/posts")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPostOutlinesByMember(@PathVariable @Positive @NotNull Long userId,
                                                     @RequestParam(required = false) @Positive Long lastPostId) {

        return postSearchService.getPostOutlinesByMember(userId, lastPostId);
    }

    @GetMapping("/posts/{postId}")
    @ResponseStatus(OK)
    @LoginCheck
    public PostDetail getPostDetail(@PathVariable @NotNull @Positive Long postId) {
        return postSearchService.getPostDetail(postId);
    }
}
