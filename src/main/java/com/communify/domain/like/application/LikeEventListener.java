package com.communify.domain.like.application;

import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikeRequest;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dao.PushRepository;
import com.communify.domain.push.dto.InfoForNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeEventListener {

    private final PushRepository pushRepository;
    private final PushService pushService;

    @Async
    @Transactional
    @EventListener
    public void pushLikeNotification(final LikeEvent event) {
        final List<LikeRequest> likeRequestList = event.getLikeRequestList();

        final List<InfoForNotification> infoList = pushRepository
                .findInfoForLikeNotificationList(likeRequestList);

        infoList.stream()
                .filter(InfoForNotification::isPushable)
                .map(InfoForNotification::generatePushRequest)
                .forEach(pushService::push);

        pushRepository.setNotified(likeRequestList);
    }
}
