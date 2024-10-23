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
    public void addComment(@PathVariable @NotNull @Positive Long postId,
                           @RequestBody @Valid CommentForm form,
                           @MemberId Long writerId,
                           @MemberName String writerName) {

        commentService.addComment(postId, form.getContent(), writerId, writerName);
    }

    @GetMapping("/{postId}/comments")
    @ResponseStatus(OK)
    @LoginCheck
    public List<CommentInfo> getComments(@PathVariable @NotNull @Positive Long postId,
                                         @RequestParam(required = false) @Positive Long lastCommentId) {

        return commentService.getComments(postId, lastCommentId)
                .getCommentInfoList();
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void editComment(@PathVariable @NotNull @Positive Long postId,
                            @PathVariable @NotNull @Positive Long commentId,
                            @RequestBody @Valid CommentForm form,
                            @MemberId Long requesterId) {

        commentService.editComment(postId, commentId, form.getContent(), requesterId);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void deleteComment(@PathVariable @NotNull @Positive Long postId,
                              @PathVariable @NotNull @Positive Long commentId,
                              @MemberId Long requesterId) {

        commentService.deleteComment(postId, commentId, requesterId);
    }
}
