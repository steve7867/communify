package com.communify.domain.follow;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.member.MemberService;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfo;
import com.communify.domain.push.dto.PushInfoForFollow;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FollowEventListener {

    private final MemberService memberService;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushFollowNotification(final FollowEvent event) {
        final Long followeeId = event.getFolloweeId();
        final String followerName = event.getFollowerName();

        final Optional<String> tokenOpt = memberService.getToken(followeeId);
        if (tokenOpt.isEmpty()) {
            return;
        }

        final String token = tokenOpt.get();

        final PushInfo pushInfo = new PushInfoForFollow(token, followerName);

        pushService.push(pushInfo);
    }
}
