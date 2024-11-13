package com.communify.domain.user;

import com.communify.domain.user.dto.UserWithdrawEvent;
import com.communify.domain.user.repository.UserRepository;
import com.communify.global.application.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWithdrawEventListener {

    private final UserRepository userRepository;
    private final CacheService cacheService;

    @Async
    @EventListener
    public void updateFollowInfoAndDeleteUser(UserWithdrawEvent event) {
        Long userId = event.getUserId();

        userRepository.decFollowerCountOfFollowees(userId, 1);
        userRepository.decFolloweeCountOfFollowers(userId, 1);
        userRepository.deleteById(userId);

        cacheService.removeFromDeletedUser(userId);
    }
}
