package com.communify.domain.like;

import com.communify.domain.like.dto.LikeEvent;
import com.communify.domain.member.MemberService;
import com.communify.domain.post.dto.HotPostChecker;
import com.communify.domain.post.service.PostEditService;
import com.communify.domain.post.service.PostSearchService;
import com.communify.domain.push.PushService;
import com.communify.domain.push.dto.PushInfoForLike;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikeEventListener {

    private final PostSearchService postSearchService;
    private final PostEditService postEditService;
    private final MemberService memberService;
    private final PushService pushService;

    @Async
    @EventListener
    public void promoteToHotIfEligible(LikeEvent event) {
        Long postId = event.getPostId();

        Optional<HotPostChecker> checkerOpt = postSearchService.getHotPostChecker(postId);
        if (checkerOpt.isEmpty()) {
            return;
        }

        HotPostChecker checker = checkerOpt.get();
        if (!checker.isEligible()) {
            return;
        }

        postEditService.promoteToHot(postId);
    }

    @Async
    @EventListener
    public void pushNotification(LikeEvent event) {
        Long postId = event.getPostId();
        List<Long> likerIdList = event.getLikerIdList();

        Optional<Long> writerIdOpt = postSearchService.getWriterId(postId);
        if (writerIdOpt.isEmpty()) {
            return;
        }
        Long writerId = writerIdOpt.get();

        Optional<String> tokenOpt = memberService.getToken(writerId);
        if (tokenOpt.isEmpty()) {
            return;
        }
        String token = tokenOpt.get();

        likerIdList.remove(writerId);

        List<String> likerNameList = memberService.getMemberNames(likerIdList);
        for (String name : likerNameList) {
            pushService.push(new PushInfoForLike(token, name));
        }
    }
}
