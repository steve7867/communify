package com.communify.domain.follow.applilcation;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.error.exception.FcmTokenNotSetException;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dto.MessageDto;
import com.communify.domain.push.dto.PushRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FollowEventListener {

    public static final String TITLE_FORMAT = "%s님이 회원님을 팔로우했습니다.";
    private final MemberFindService memberFindService;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushFollowNotification(final FollowEvent event) {
        final String memberName = event.getMemberName();
        final Long followId = event.getFollowId();

        final String token = memberFindService.findFcmTokenById(followId)
                .orElseThrow(() -> new FcmTokenNotSetException(followId));

        final MessageDto messageDto = MessageDto.builder()
                .title(String.format(TITLE_FORMAT, memberName))
                .build();

        pushService.push(new PushRequest(token, messageDto));
    }
}
