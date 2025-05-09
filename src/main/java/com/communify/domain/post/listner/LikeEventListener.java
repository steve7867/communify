package com.communify.domain.post.listner;

import com.communify.domain.post.dto.LikeEvent;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForLike;
import com.communify.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeEventListener {

    private final UserRepository userRepository;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushNotification(LikeEvent event) {
        Long postId = event.getPostId();

        String token = userRepository.findTokenOfPostWriter(postId);

        pushService.push(new PushInfoForLike(token));
    }
}
