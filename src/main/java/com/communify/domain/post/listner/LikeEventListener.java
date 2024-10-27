package com.communify.domain.post.listner;

import com.communify.domain.member.MemberService;
import com.communify.domain.post.PostRepository;
import com.communify.domain.post.dto.HotPostChecker;
import com.communify.domain.post.dto.LikeEvent;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikeEventListener {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final PushService pushService;

    @Async
    @EventListener
    public void promoteToHotIfEligible(LikeEvent event) {
        Long postId = event.getPostId();

        Optional<HotPostChecker> checkerOpt = postRepository.findHotPostChecker(postId);
        if (checkerOpt.isEmpty() || !checkerOpt.get().isEligible()) {
            return;
        }

        postRepository.promoteToHot(postId);
    }

    @Async
    @EventListener
    public void pushNotification(LikeEvent event) {
        Long postId = event.getPostId();

        Optional<String> tokenOpt = memberService.getTokenOfPostWriter(postId);
        if (tokenOpt.isEmpty()) {
            return;
        }

        pushService.push(new PushInfoForLike(tokenOpt.get()));
    }
}
