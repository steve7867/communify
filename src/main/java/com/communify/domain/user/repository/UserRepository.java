package com.communify.domain.user.repository;

import com.communify.domain.user.dto.UserInfoForLogin;
import com.communify.domain.user.dto.UserInfoForSearch;
import com.communify.domain.user.exception.EmailAlreadyUsedException;
import com.communify.domain.user.exception.NameAlreadyUsedException;
import com.communify.domain.user.exception.TokenNotFoundException;
import com.communify.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public Integer insert(String email, String hashed, String name) {
        try {
            return userMapper.insert(email, hashed, name);
        } catch (DuplicateKeyException e) {
            String message = e.getMessage();
            if (message.contains(String.format("Duplicate entry '%s' for key 'user.email'", email))) {
                throw new EmailAlreadyUsedException(email, e);
            } else {
                throw new NameAlreadyUsedException(name, e);
            }
        }
    }

    @Transactional(readOnly = true)
    public UserInfoForLogin findUserInfoForLoginByEmail(String email) {
        return userMapper.findUserInfoForLoginByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional(readOnly = true)
    public UserInfoForSearch findUserInfoForSearch(Long userId, Long searcherId) {
        return userMapper.findUserInfoForSearch(userId, searcherId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional(readOnly = true)
    public String findHashed(Long userId) {
        return userMapper.findHashed(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void deleteById(Long userId) {
        userMapper.deleteById(userId);
    }

    public void setToken(String token, Long userId) {
        userMapper.setToken(token, userId);
    }

    @Transactional(readOnly = true)
    public String findTokenById(Long userId) {
        return userMapper.findTokenById(userId)
                .orElseThrow(TokenNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<String> findTokensOfFollowers(Long userId) {
        List<String> tokenList = userMapper.findTokensOfFollowers(userId);
        return Collections.unmodifiableList(tokenList);
    }

    public void updatePassword(String newHashed, Long userId) {
        userMapper.updatePassword(newHashed, userId);
    }

    public void incFollowerCount(Long followeeId, Integer count) {
        userMapper.incFollowerCount(followeeId, count);
    }

    public void incFolloweeCount(Long followerId, Integer count) {
        userMapper.incFolloweeCount(followerId, count);
    }

    public void decFollowerCount(Long followeeId, Integer count) {
        userMapper.decFollowerCount(followeeId, count);
    }

    public void decFolloweeCount(Long followerId, Integer count) {
        userMapper.decFolloweeCount(followerId, count);
    }

    public void decFollowerCountOfFollowees(Long followerId, Integer count) {
        userMapper.decFollowerCountOfFollowees(followerId, count);
    }

    public void decFolloweeCountOfFollowers(Long followeeId, Integer count) {
        userMapper.decFolloweeCountOfFollowers(followeeId, count);
    }

    @Transactional(readOnly = true)
    public String findTokenOfPostWriter(Long postId) {
        return userMapper.findTokenOfPostWriter(postId).orElseThrow(TokenNotFoundException::new);
    }
}
