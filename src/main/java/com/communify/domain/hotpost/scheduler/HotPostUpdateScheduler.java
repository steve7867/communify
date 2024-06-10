package com.communify.domain.hotpost.scheduler;

import com.communify.domain.hotpost.application.HotPostUpdateService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotPostUpdateScheduler {

    private final HotPostUpdateService hotPostUpdateService;

    @Scheduled(cron = "0 0 0/1 * * *")
    @SchedulerLock(name = "HotPostUpdateScheduler_updateHotPosts")
    public void updateHotPosts() {
        hotPostUpdateService.updateHotPosts();
    }
}
