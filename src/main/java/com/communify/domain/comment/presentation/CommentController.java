package com.communify.domain.comment.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.comment.application.CommentService;
import com.communify.domain.comment.dto.CommentDeleteRequest;
import com.communify.domain.comment.dto.CommentEditRequest;
import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.domain.comment.dto.incoming.CommentForm;
import com.communify.domain.comment.dto.outgoing.CommentInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    @ResponseStatus(OK)
    @LoginCheck
    public void addComment(@PathVariable @NotNull @Positive final Long postId,
                           @RequestBody @Valid final CommentForm form,
                           @MemberId final Long memberId,
                           @MemberName final String memberName) {

        final CommentUploadRequest request = CommentUploadRequest.builder()
                .postId(postId)
                .content(form.getContent())
                .memberId(memberId)
                .memberName(memberName)
                .build();

        commentService.addComment(request);
    }

    @GetMapping("/{postId}/comments")
    @ResponseStatus(OK)
    @LoginCheck
    public List<CommentInfo> getComments(@PathVariable @NotNull @Positive final Long postId) {
        return commentService.getComments(postId);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void editComment(@PathVariable @NotNull @Positive final Long postId,
                            @PathVariable @NotNull @Positive final Long commentId,
                            @RequestBody @Valid final CommentForm form,
                            @MemberId final Long memberId) {

        final CommentEditRequest request = CommentEditRequest.builder()
                .postId(postId)
                .commentId(commentId)
                .content(form.getContent())
                .memberId(memberId)
                .build();

        commentService.editComment(request);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void deleteComment(@PathVariable @NotNull @Positive final Long postId,
                              @PathVariable @NotNull @Positive final Long commentId,
                              @MemberId final Long memberId) {

        final CommentDeleteRequest request = CommentDeleteRequest.builder()
                .postId(postId)
                .commentId(commentId)
                .memberId(memberId)
                .build();

        commentService.deleteComment(request);
    }
}
