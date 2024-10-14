package com.communify.domain.post;

import com.communify.domain.post.dto.PostUploadEvent;
import com.communify.domain.push.PushRepository;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForPostUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostUploadEventListener {

    private final PushRepository pushRepository;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushPostUploadNotification(final PostUploadEvent event) {
        final Long writerId = event.getWriterId();
        final String writerName = event.getWriterName();

        final List<String> tokenList = pushRepository.findTokenOfWritersFollower(writerId);

        tokenList.forEach(token -> {
            pushService.push(new PushInfoForPostUpload(token, writerName));
        });
    }
}
