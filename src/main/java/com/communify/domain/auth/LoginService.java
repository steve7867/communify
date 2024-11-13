package com.communify.domain.auth;

import com.communify.domain.auth.exception.AccountDeletedException;
import com.communify.domain.auth.exception.InvalidPasswordException;
import com.communify.domain.user.dto.UserInfoForLogin;
import com.communify.domain.user.repository.UserRepository;
import com.communify.global.application.CacheService;
import com.communify.global.application.SessionService;
import com.communify.global.util.PasswordEncryptor;
import com.communify.global.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final CacheService cacheService;

    public void login(String email, String password) {
        UserInfoForLogin userInfo = userRepository.findUserInfoForLoginByEmail(email);

        boolean deleted = cacheService.isDeletedUser(userInfo.getId());
        if (deleted) {
            throw new AccountDeletedException();
        }

        String hashed = userInfo.getHashed();
        if (!PasswordEncryptor.match(password, hashed)) {
            throw new InvalidPasswordException(password);
        }

        sessionService.add(SessionKey.USER_ID, userInfo.getId());
        sessionService.add(SessionKey.USER_NAME, userInfo.getName());
    }

    public void logout() {
        sessionService.invalidate();
    }

    public boolean isLoggedIn() {
        return sessionService.get(SessionKey.USER_ID).isPresent();
    }
}
