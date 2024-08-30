package com.communify.domain.post.presentation.controller;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.post.dto.AllHotPostSearchCondition;
import com.communify.domain.post.application.HotPostSearchService;
import com.communify.domain.post.application.PostEditService;
import com.communify.domain.post.application.PostSearchService;
import com.communify.domain.post.dto.PostOutlineSearchConditionByCategory;
import com.communify.domain.post.dto.PostOutlineSearchConditionByWriter;
import com.communify.domain.post.dto.outgoing.PostDetail;
import com.communify.domain.post.dto.outgoing.PostOutline;
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
    private final HotPostSearchService hotPostSearchService;

    @GetMapping("/categories/{categoryId}/posts")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPostOutlinesByCategory(@PathVariable @Positive @NotNull final Long categoryId,
                                                       @RequestParam(required = false) @Positive final Long lastPostId) {

        final PostOutlineSearchConditionByCategory searchCond =
                new PostOutlineSearchConditionByCategory(categoryId, lastPostId);

        return postSearchService.getPostOutlinesByCategory(searchCond);
    }

    @GetMapping("/members/{writerId}/posts")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPostOutlinesByMember(@PathVariable @Positive @NotNull final Long writerId,
                                                     @RequestParam(required = false) @Positive final Long lastPostId) {

        final PostOutlineSearchConditionByWriter searchCond =
                new PostOutlineSearchConditionByWriter(writerId, lastPostId);

        return postSearchService.getPostOutlinesByWriter(searchCond);
    }

    @GetMapping("/posts/{postId}")
    @LoginCheck
    public ResponseEntity<PostDetail> getPostDetail(@PathVariable @NotNull @Positive final Long postId,
                                                    @MemberId final Long memberId) {

        final Optional<PostDetail> postDetailOpt = postSearchService.getPostDetail(postId, memberId);
        if (postDetailOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        postEditService.incrementView(postId);

        return ResponseEntity.ok(postDetailOpt.get());
    }

    @GetMapping("/posts/hot")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getAllHotPostOutlineList(@RequestParam @Positive final Long lastPostId) {
        final AllHotPostSearchCondition searchCond = new AllHotPostSearchCondition(lastPostId);
        return hotPostSearchService.getAllHotPostOutlineList(searchCond);
    }
}
