package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentUploadEvent;
import com.communify.domain.push.PushRepository;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final PushRepository pushRepository;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushCommentUploadNotification(final CommentUploadEvent event) {
        final Long postId = event.getPostId();
        final String content = event.getContent();
        final String writerName = event.getWriterName();

        final String token = pushRepository.findTokenOfPostWriter(postId);

        final PushInfoForComment pushInfo = new PushInfoForComment(token, content, writerName);

        pushService.push(pushInfo);
    }
}
