package com.communify.domain.comment;

import com.communify.domain.comment.dto.CommentAddEvent;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForComment;
import com.communify.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final UserRepository userRepository;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushNotification(CommentAddEvent event) {
        Long postId = event.getPostId();

        String token = userRepository.findTokenOfPostWriter(postId);

        pushService.push(new PushInfoForComment(token));
    }
}
