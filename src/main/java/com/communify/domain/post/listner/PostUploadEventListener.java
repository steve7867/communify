package com.communify.domain.post.listner;

import com.communify.domain.post.dto.PostUploadEvent;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForPostUpload;
import com.communify.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PostUploadEventListener {

    private final UserRepository userRepository;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushNotification(PostUploadEvent event) {
        Long writerId = event.getWriterId();
        String writerName = event.getWriterName();

        userRepository.findTokensOfFollowers(writerId)
                .stream()
                .filter(Objects::nonNull)
                .map(token -> new PushInfoForPostUpload(token, writerName))
                .forEach(pushService::push);
    }
}
