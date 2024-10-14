package com.communify.domain.comment;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.comment.dto.CommentForm;
import com.communify.domain.comment.dto.CommentInfo;
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
import org.springframework.web.bind.annotation.RequestParam;
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
                           @MemberId final Long writerId,
                           @MemberName final String writerName) {

        commentService.addComment(postId, form.getContent(), writerId, writerName);
    }

    @GetMapping("/{postId}/comments")
    @ResponseStatus(OK)
    @LoginCheck
    public List<CommentInfo> getComments(@PathVariable @NotNull @Positive final Long postId,
                                         @RequestParam(required = false) @Positive final Long lastCommentId) {

        return commentService.getComments(postId, lastCommentId)
                .getCommentInfoList();
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void editComment(@PathVariable @NotNull @Positive final Long postId,
                            @PathVariable @NotNull @Positive final Long commentId,
                            @RequestBody @Valid final CommentForm form,
                            @MemberId final Long requesterId) {

        commentService.editComment(postId, commentId, form.getContent(), requesterId);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void deleteComment(@PathVariable @NotNull @Positive final Long postId,
                              @PathVariable @NotNull @Positive final Long commentId,
                              @MemberId final Long requesterId) {

        commentService.deleteComment(postId, commentId, requesterId);
    }
}
