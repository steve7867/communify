package com.communify.domain.comment.application;

import com.communify.domain.comment.dao.CommentRepository;
import com.communify.domain.comment.dto.PostWriterInfoForNotification;
import com.communify.domain.comment.dto.event.CommentUploadEvent;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dto.MessageDto;
import com.communify.domain.push.dto.PushRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final PushService pushService;
    private final CommentRepository commentRepository;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushCommentUploadNotification(final CommentUploadEvent event) {
        final Long postId = event.getPostId();
        final String commentContent = event.getCommentContent();
        final Long commentWriterId = event.getCommentWriterId();
        final String commentWriterName = event.getCommentWriterName();

        final Optional<PostWriterInfoForNotification> postWriterInfoOpt = commentRepository
                .findPostWriterInfoForNotification(postId);

        if (postWriterInfoOpt.isEmpty()) {
            return;
        }

        final PostWriterInfoForNotification postWriterInfo = postWriterInfoOpt.get();

        if (Objects.equals(postWriterInfo.getWriterId(), commentWriterId) || !postWriterInfo.isFcmTokenExisting()) {
            return;
        }

        final MessageDto messageDto = MessageDto.forComment(commentWriterName, commentContent);
        pushService.push(new PushRequest(postWriterInfo.getFcmToken(), messageDto));
    }
}
