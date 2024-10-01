package com.communify.domain.follow.applilcation;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.follow.dto.FollowRequest;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dao.PushRepository;
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
        final FollowRequest request = event.getFollowRequest();

        final PushInfoForFollow info = pushRepository.findPushInfoForFollow(request);

        pushService.push(info);
    }
}
