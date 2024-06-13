package com.communify.domain.comment.application;

import com.communify.domain.comment.dto.event.CommentUploadEvent;
import com.communify.domain.comment.dto.CommentUploadRequest;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.error.exception.FcmTokenNotSetException;
import com.communify.domain.post.application.PostSearchService;
import com.communify.domain.post.error.exception.PostWriterNotFoundException;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dto.MessageDto;
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
    public void pushCommentUploadNotification(CommentUploadEvent event) {
        CommentUploadRequest request = event.getCommentUploadRequest();

        Long requesterId = request.getMemberId();
        String requesterName = request.getMemberName();
        String content = request.getContent();
        Long postId = request.getPostId();

        Long writerId = postSearchService.getWriterId(postId)
                .orElseThrow(() -> new PostWriterNotFoundException(postId));

        if (Objects.equals(requesterId, writerId)) {
            return;
        }

        String token = memberFindService.findFcmTokenById(writerId)
                .orElseThrow(() -> new FcmTokenNotSetException(writerId));

        MessageDto messageDto = MessageDto.builder()
                .title(String.format("%s님이 회원님의 게시글에 댓글을 작성하였습니다.", requesterName))
                .body(content)
                .build();

        pushService.push(token, messageDto);
    }
}
