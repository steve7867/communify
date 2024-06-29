package com.communify.domain.comment.application;

import com.communify.domain.comment.dto.event.CommentUploadEvent;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.error.exception.FcmTokenNotSetException;
import com.communify.domain.post.application.PostSearchService;
import com.communify.domain.post.error.exception.PostWriterNotFoundException;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dto.MessageDto;
import com.communify.domain.push.dto.PushRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final PostSearchService postSearchService;
    private final PushService pushService;
    private final MemberFindService memberFindService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushCommentUploadNotification(final CommentUploadEvent event) {
        final Long commentWriterId = event.getCommentWriterId();
        final Long postId = event.getPostId();

        final Long postWriterId = postSearchService.getWriterId(postId)
                .orElseThrow(() -> new PostWriterNotFoundException(postId));

        if (Objects.equals(commentWriterId, postWriterId)) {
            return;
        }

        final String token = memberFindService.findFcmTokenById(postWriterId)
                .orElseThrow(() -> new FcmTokenNotSetException(postWriterId));

        final MessageDto messageDto = MessageDto.forComment(event.getCommentWriterName(), event.getCommentContent());

        pushService.push(new PushRequest(token, messageDto));
    }
}
