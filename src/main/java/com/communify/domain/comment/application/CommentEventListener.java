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

    public static final String TITLE_FORMAT = "%s님이 회원님의 게시글에 댓글을 작성하였습니다.";
    private final PostSearchService postSearchService;
    private final PushService pushService;
    private final MemberFindService memberFindService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushCommentUploadNotification(final CommentUploadEvent event) {
        final Long requesterId = event.getMemberId();
        final String requesterName = event.getMemberName();
        final String content = event.getContent();
        final Long postId = event.getPostId();

        final Long writerId = postSearchService.getWriterId(postId)
                .orElseThrow(() -> new PostWriterNotFoundException(postId));

        if (Objects.equals(requesterId, writerId)) {
            return;
        }

        final String token = memberFindService.findFcmTokenById(writerId)
                .orElseThrow(() -> new FcmTokenNotSetException(writerId));

        final MessageDto messageDto = MessageDto.builder()
                .title(String.format(TITLE_FORMAT, requesterName))
                .body(content)
                .build();

        pushService.push(new PushRequest(token, messageDto));
    }
}
