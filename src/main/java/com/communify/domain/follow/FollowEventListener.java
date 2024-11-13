package com.communify.domain.follow;

import com.communify.domain.follow.dto.FollowEvent;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForFollow;
import com.communify.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowEventListener {

    private final UserRepository userRepository;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushNotification(FollowEvent event) {
        Long followeeId = event.getFolloweeId();
        String followerName = event.getFollowerName();

        String token = userRepository.findTokenById(followeeId);

        pushService.push(new PushInfoForFollow(token, followerName));
    }
}
