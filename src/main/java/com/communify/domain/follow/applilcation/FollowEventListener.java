package com.communify.domain.follow.applilcation;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.error.exception.FcmTokenNotSetException;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dto.MessageDto;
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
    public void pushFollowNotification(FollowEvent event) {
        FollowRequest request = event.getFollowRequest();

        String memberName = request.getMemberName();
        Long followId = request.getFollowId();

        String token = memberFindService.findFcmTokenById(followId)
                .orElseThrow(() -> new FcmTokenNotSetException(followId));

        String title = String.format("%s님이 회원님을 팔로우했습니다.", memberName);
        MessageDto messageDto = MessageDto.builder()
                .title(title)
                .build();

        pushService.push(token, messageDto);
    }
}
