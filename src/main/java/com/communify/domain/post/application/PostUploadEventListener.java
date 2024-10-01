package com.communify.domain.post.application;

import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.post.dto.event.PostUploadEvent;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dao.PushRepository;
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
        final PostUploadRequest request = event.getPostUploadRequest();

        final List<PushInfoForPostUpload> infoList = pushRepository
                .findPushInfoForPostUploadList(request);

        infoList.forEach(pushService::push);
    }
}
