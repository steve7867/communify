package com.communify.domain.post.application;

import com.communify.domain.follow.dao.FollowRepository;
import com.communify.domain.follow.dto.FollowerInfoForNotification;
import com.communify.domain.post.dto.event.PostUploadEvent;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dto.MessageDto;
import com.communify.domain.push.dto.PushRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostUploadEventListener {

    private final FollowRepository followRepository;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushPostUploadNotification(final PostUploadEvent event) {
        final Long writerId = event.getWriterId();
        final String writerName = event.getWriterName();

        final List<FollowerInfoForNotification> infoList = followRepository
                .findFollowerInfoForNotificationList(writerId);

        final MessageDto messageDto = MessageDto.forPostUpload(writerName);

        infoList.stream()
                .filter(FollowerInfoForNotification::isFcmTokenExisting)
                .forEach(info -> pushService.push(new PushRequest(info.getFcmToken(), messageDto)));
    }
}
