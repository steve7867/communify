package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentUploadEvent;
import com.communify.domain.member.MemberService;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final MemberService memberService;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushNotification(CommentUploadEvent event) {
        Long postId = event.getPostId();

        Optional<String> tokenOpt = memberService.getTokenOfPostWriter(postId);
        if (tokenOpt.isEmpty()) {
            return;
        }

        pushService.push(new PushInfoForComment(tokenOpt.get()));
    }
}
