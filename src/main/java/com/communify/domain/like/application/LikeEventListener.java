package com.communify.domain.like.application;

import com.communify.domain.like.dao.LikeRepository;
import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.like.dto.LikeInfoForNotification;
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
        final List<LikeInfoForNotification> infoList = likeRepository
                .findLikeInfoForNotificationList(likeRequestList);

        final List<LikeInfoForNotification> qualifiedInfoList = infoList.stream()
                .filter(LikeInfoForNotification::isPostWriterExist)
                .filter(LikeInfoForNotification::isFcmTokenExist)
                .filter(info -> !info.isWriterSameAsLiker())
                .filter(LikeInfoForNotification::isFresh)
                .toList();

        for (LikeInfoForNotification info : qualifiedInfoList) {
            final MessageDto messageDto = MessageDto.forPostLike(info.getLikerName());
            pushService.push(new PushRequest(info.getFcmToken(), messageDto));
        }

        likeRepository.setNotified(infoList);
    }
}
