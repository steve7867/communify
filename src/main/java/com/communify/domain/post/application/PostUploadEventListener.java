package com.communify.domain.post.application;

import com.communify.domain.follow.applilcation.FollowService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.post.dto.PostUploadEvent;
import com.communify.domain.push.application.PushService;
import com.communify.domain.push.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PostUploadEventListener {

    private final FollowService followService;
    private final PushService pushService;

    @Transactional(readOnly = true)
    @EventListener
    public void pushPostUploadNotification(PostUploadEvent event) {
        Long memberId = event.getMemberId();
        String memberName = event.getMemberName();

        List<MemberInfo> followerList = followService.getFollowers(memberId);

        MessageDto messageDto = MessageDto.builder()
                .title(String.format("%s님이 새로운 게시글을 작성했습니다.", memberName))
                .build();

        followerList.stream()
                .map(MemberInfo::getFcmToken)
                .filter(Objects::nonNull)
                .forEach(token -> pushService.push(token, messageDto));
    }
}
