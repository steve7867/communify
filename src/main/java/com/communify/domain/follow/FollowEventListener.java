package com.communify.domain.follow;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.member.MemberService;
import com.communify.domain.push.PushService;
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
    public void pushNotification(FollowEvent event) {
        Long followeeId = event.getFolloweeId();
        String followerName = event.getFollowerName();

        Optional<String> tokenOpt = memberService.getToken(followeeId);
        if (tokenOpt.isEmpty()) {
            return;
        }
        String token = tokenOpt.get();

        pushService.push(new PushInfoForFollow(token, followerName));
    }
}
