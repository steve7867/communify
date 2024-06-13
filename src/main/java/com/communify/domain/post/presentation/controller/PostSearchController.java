package com.communify.domain.post.presentation.controller;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.post.application.PostEditService;
import com.communify.domain.post.application.PostSearchService;
import com.communify.domain.post.dto.incoming.PostOutlineSearchCondition;
import com.communify.domain.post.dto.outgoing.PostDetail;
import com.communify.domain.post.dto.outgoing.PostOutline;
import com.communify.domain.post.presentation.validator.PostOutlineSearchConditionValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;
    private final PostEditService postEditService;
    private final PostOutlineSearchConditionValidator postOutlineSearchConditionValidator;

    @GetMapping
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPostOutlines(@ModelAttribute @Valid PostOutlineSearchCondition searchCond) {
        return postSearchService.getPostOutlineList(searchCond);
    }

    @InitBinder("postOutlineSearchCondition")
    public void addPostOutlineSearchConditionValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(postOutlineSearchConditionValidator);
    }

    @GetMapping("/{postId}")
    @LoginCheck
    public ResponseEntity<PostDetail> getPostDetail(@PathVariable @NotNull @Positive Long postId,
                                                    @MemberId Long memberId) {

        Optional<PostDetail> postDetailOpt = postSearchService.getPostDetail(postId, memberId);
        if (postDetailOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        postEditService.incrementView(postId, memberId);

        return ResponseEntity.ok(postDetailOpt.get());
    }
}
