package com.communify.domain.comment.application;

import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.domain.comment.dto.event.CommentUploadEvent;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dao.PushRepository;
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
        final CommentUploadRequest request = event.getCommentUploadRequest();

        final PushInfoForComment info = pushRepository
                .findPushInfoForComment(request);

        pushService.push(info);
    }
}
