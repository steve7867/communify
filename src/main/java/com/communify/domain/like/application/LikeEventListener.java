package com.communify.domain.like.application;

import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.error.exception.FcmTokenNotSetException;
import com.communify.domain.post.application.PostSearchService;
import com.communify.domain.post.error.exception.PostNotFoundException;
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
public class LikeEventListener {

    public static final String TITLE_FORMAT = "%s님이 회원님의 게시글에 '좋아요'를 눌렀습니다.";
    private final PostSearchService postSearchService;
    private final MemberFindService memberFindService;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushLikeNotification(final LikeEvent event) {
        final String memberName = event.getMemberName();
        final Long memberId = event.getMemberId();
        final Long postId = event.getPostId();

        final Long writerId = postSearchService.getWriterId(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (Objects.equals(memberId, writerId)) {
            return;
        }

        final String token = memberFindService.findFcmTokenById(writerId)
                .orElseThrow(() -> new FcmTokenNotSetException(writerId));

        final MessageDto messageDto = MessageDto.builder()
                .title(String.format(TITLE_FORMAT, memberName))
                .build();

        pushService.push(new PushRequest(token, messageDto));
    }
}
