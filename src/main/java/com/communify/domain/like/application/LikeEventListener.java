package com.communify.domain.like.application;

import com.communify.domain.like.dao.LikeRepository;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikeNotificationInfo;
import com.communify.domain.like.dto.LikeRequest;
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
public class LikeEventListener {

    private final LikeRepository likeRepository;
    private final PushService pushService;

    @Async
    @Transactional
    @EventListener
    public void pushLikeNotification(final LikeEvent event) {
        final List<LikeRequest> likeRequestList = event.getLikeRequestList();
        final List<LikeNotificationInfo> filteredLikeNotificationInfoList = likeRepository.findFilteredLikeNotificationInfoList(likeRequestList);

        for (LikeNotificationInfo info : filteredLikeNotificationInfoList) {
            final MessageDto messageDto = MessageDto.forPostLike(info.getLikerName());
            pushService.push(new PushRequest(info.getFcmToken(), messageDto));
        }

        likeRepository.setNotified(filteredLikeNotificationInfoList);
    }
}
