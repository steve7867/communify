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

    private final MemberFindService memberFindService;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushFollowNotification(final FollowEvent event) {
        final String followerName = event.getFollowerName();
        final Long followId = event.getFollowId();

        final String token = memberFindService.findFcmTokenById(followId)
                .orElseThrow(() -> new FcmTokenNotSetException(followId));

        final MessageDto messageDto = MessageDto.forFollow(followerName);

        pushService.push(new PushRequest(token, messageDto));
    }
}
