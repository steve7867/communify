package com.communify.domain.like;

import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.push.PushRepository;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeEventListener {

    private final PushRepository pushRepository;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushLikeNotification(final LikeEvent event) {
        final Long postId = event.getPostId();
        final List<Long> likerIdList = event.getLikerIdList();

        final List<PushInfoForLike> infoList = pushRepository.findPushInfoForLikeList(postId, likerIdList);

        final List<PushInfoForLike> sentInfoList = infoList.stream()
                .filter(pushService::push)
                .toList();

        pushRepository.setPushStateAsSent(sentInfoList);
    }
}
