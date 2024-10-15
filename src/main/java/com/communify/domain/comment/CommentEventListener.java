package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentUploadEvent;
import com.communify.domain.member.MemberService;
import com.communify.domain.post.service.PostSearchService;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfo;
import com.communify.domain.push.dto.PushInfoForComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final PostSearchService postSearchService;
    private final MemberService memberService;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushCommentUploadNotification(final CommentUploadEvent event) {
        final Long postId = event.getPostId();
        final String content = event.getContent();
        final String commentWriterName = event.getWriterName();

        final Optional<Long> postWriterIdOpt = postSearchService.getWriterId(postId);
        if (postWriterIdOpt.isEmpty()) {
            return;
        }
        final Long postWriterId = postWriterIdOpt.get();

        final Optional<String> tokenOpt = memberService.getToken(postWriterId);
        if (tokenOpt.isEmpty()) {
            return;
        }
        final String token = tokenOpt.get();

        final PushInfo pushInfo = new PushInfoForComment(token, content, commentWriterName);

        pushService.push(pushInfo);
    }
}
