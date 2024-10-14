package com.communify.domain.follow;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.push.PushRepository;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForFollow;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FollowEventListener {

    private final PushRepository pushRepository;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushFollowNotification(final FollowEvent event) {
        final Long followeeId = event.getFolloweeId();
        final String followerName = event.getFollowerName();

        final String token = pushRepository.findTokenOfFollowee(followeeId);

        final PushInfoForFollow pushInfo = new PushInfoForFollow(token, followerName);

        pushService.push(pushInfo);
    }
}
