package com.communify.domain.post.application;

import com.communify.domain.follow.applilcation.FollowService;
import com.communify.domain.member.dto.outgoing.MemberInfo;
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
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PostUploadEventListener {

    public static final String TITLE_FORMAT = "%s님이 새로운 게시글을 작성했습니다.";
    private final FollowService followService;
    private final PushService pushService;

    @Async
    @Transactional(readOnly = true)
    @EventListener
    public void pushPostUploadNotification(final PostUploadEvent event) {
        final Long memberId = event.getMemberId();
        final String memberName = event.getMemberName();

        final List<MemberInfo> followerList = followService.getFollowers(memberId);

        final MessageDto messageDto = MessageDto.builder()
                .title(String.format(TITLE_FORMAT, memberName))
                .build();

        followerList.stream()
                .map(MemberInfo::getFcmToken)
                .filter(Objects::nonNull)
                .forEach(token -> pushService.push(new PushRequest(token, messageDto)));
    }
}
