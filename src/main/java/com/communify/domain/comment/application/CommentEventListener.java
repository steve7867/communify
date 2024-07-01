package com.communify.domain.comment.application;

import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.domain.comment.dto.event.CommentUploadEvent;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dao.PushRepository;
import com.communify.domain.push.dto.InfoForNotification;
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
        final CommentUploadRequest commentUploadRequest = event.getCommentUploadRequest();

        final InfoForNotification info = pushRepository
                .findInfoForCommentNotification(commentUploadRequest);

        if (!info.isPushable()) {
            return;
        }

        pushService.push(info.generatePushRequest());
    }
}
