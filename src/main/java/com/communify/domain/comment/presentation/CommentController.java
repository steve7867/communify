package com.communify.domain.comment.presentation;

import com.communify.domain.auth.annotation.CurrentMemberId;
import com.communify.domain.auth.annotation.CurrentMemberName;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.comment.application.CommentService;
import com.communify.domain.comment.dto.incoming.CommentContainer;
import com.communify.domain.comment.dto.CommentDeleteRequest;
import com.communify.domain.comment.dto.CommentEditRequest;
import com.communify.domain.comment.dto.outgoing.CommentInfo;
import com.communify.domain.comment.dto.CommentUploadRequest;
import jakarta.validation.Valid;
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
    public void addComment(@PathVariable Long postId,
                           @RequestBody @Valid CommentContainer commentContainer,
                           @CurrentMemberId Long memberId,
                           @CurrentMemberName String memberName) {

        String content = commentContainer.getContent();
        CommentUploadRequest request = new CommentUploadRequest(content, postId, memberId, memberName);
        commentService.addComment(request);
    }

    @GetMapping("/{postId}/comments")
    @ResponseStatus(OK)
    @LoginCheck
    public List<CommentInfo> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void editComment(@PathVariable Long postId,
                            @PathVariable Long commentId,
                            @RequestBody @Valid CommentContainer commentContainer,
                            @CurrentMemberId Long memberId) {

        String content = commentContainer.getContent();
        CommentEditRequest request = new CommentEditRequest(postId, commentId, content, memberId);

        commentService.editComment(request);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              @CurrentMemberId Long memberId) {

        CommentDeleteRequest request = new CommentDeleteRequest(postId, commentId, memberId);

        commentService.deleteComment(request);
    }
}
