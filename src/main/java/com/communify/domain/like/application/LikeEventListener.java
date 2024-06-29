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

    public static final String TITLE_FORMAT = "%s님이 회원님의 게시글에 '좋아요'를 눌렀습니다.";
    private final LikeRepository likeRepository;
    private final PushService pushService;

    @Async
    @Transactional
    @EventListener
    public void pushLikeNotification(final LikeEvent event) {
        final List<LikeRequest> likeRequestList = event.getLikeRequestList();
        final List<LikeNotificationInfo> filteredLikeNotificationInfoList = likeRepository.findFilteredLikeNotificationInfoList(likeRequestList);

        for (LikeNotificationInfo info : filteredLikeNotificationInfoList) {
            final MessageDto messageDto = MessageDto.builder()
                    .title(String.format(TITLE_FORMAT, info.getLikerName()))
                    .build();

            pushService.push(new PushRequest(info.getFcmToken(), messageDto));
        }

        likeRepository.setNotified(filteredLikeNotificationInfoList);
    }
}
