package com.communify.domain.user;

import com.communify.domain.auth.LoginService;
import com.communify.domain.auth.exception.InvalidPasswordException;
import com.communify.domain.user.dto.UserInfoForSearch;
import com.communify.domain.user.dto.UserWithdrawEvent;
import com.communify.domain.user.repository.UserRepository;
import com.communify.global.application.CacheService;
import com.communify.global.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginService loginService;
    private final CacheService cacheService;
    private final ApplicationEventPublisher eventPublisher;

    public void signUp(String email, String password, String name) {
        String hashed = PasswordEncryptor.encrypt(password);
        userRepository.insert(email, hashed, name);
    }

    public UserInfoForSearch getUserInfo(Long userId, Long searcherId) {
        return userRepository.findUserInfoForSearch(userId, searcherId);
    }

    public void withdraw(String password, Long userId) {
        String hashed = userRepository.findHashed(userId);

        if (!PasswordEncryptor.match(password, hashed)) {
            throw new InvalidPasswordException(password);
        }

        loginService.logout();
        cacheService.addDeletedUser(userId);

        eventPublisher.publishEvent(new UserWithdrawEvent(userId));
    }

    public void updatePassword(String currentPassword, String newPassword, Long userId) {
        String hashed = userRepository.findHashed(userId);

        if (!PasswordEncryptor.match(currentPassword, hashed)) {
            throw new InvalidPasswordException(currentPassword);
        }

        String newHashed = PasswordEncryptor.encrypt(newPassword);
        userRepository.updatePassword(newHashed, userId);
    }

    public void setToken(String token, Long userId) {
        userRepository.setToken(token, userId);
    }
}
