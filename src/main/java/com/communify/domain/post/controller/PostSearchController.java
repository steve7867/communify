package com.communify.domain.post.controller;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.service.PostEditService;
import com.communify.domain.post.service.PostSearchService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;
    private final PostEditService postEditService;

    @GetMapping("/posts/hot")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getHotPostOutlines(@RequestParam(required = false) @Positive final Long lastPostId) {
        return postSearchService.getHotPostOutlines(lastPostId);
    }

    @GetMapping("/categories/{categoryId}/posts")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPostOutlinesByCategory(@PathVariable @Positive @NotNull final Long categoryId,
                                                       @RequestParam(required = false) @Positive final Long lastPostId) {

        return postSearchService.getPostOutlinesByCategory(categoryId, lastPostId);
    }

    @GetMapping("/members/{memberId}/posts")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPostOutlinesByMember(@PathVariable @Positive @NotNull final Long memberId,
                                                     @RequestParam(required = false) @Positive final Long lastPostId) {

        return postSearchService.getPostOutlinesByMember(memberId, lastPostId);
    }

    @GetMapping("/posts/{postId}")
    @LoginCheck
    public ResponseEntity<PostDetail> getPostDetail(@PathVariable @NotNull @Positive final Long postId) {
        final Optional<PostDetail> postDetailOpt = postSearchService.getPostDetail(postId);
        if (postDetailOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        postEditService.incrementView(postId);

        return ResponseEntity.ok(postDetailOpt.get());
    }
}
