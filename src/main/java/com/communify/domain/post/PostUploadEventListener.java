package com.communify.domain.post;

import com.communify.domain.member.MemberService;
import com.communify.domain.post.dto.PostUploadEvent;
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

    private final MemberService memberService;
    private final PushService pushService;

    @Async
    @EventListener
    public void pushPostUploadNotification(final PostUploadEvent event) {
        final Long writerId = event.getWriterId();
        final String writerName = event.getWriterName();

        final List<String> tokenList = memberService.getTokensOfFollowers(writerId);

        tokenList.forEach(token -> {
            pushService.push(new PushInfoForPostUpload(token, writerName));
        });
    }
}
