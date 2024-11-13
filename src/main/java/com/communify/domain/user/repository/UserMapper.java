package com.communify.domain.user.repository;

import com.communify.domain.user.dto.UserInfoForLogin;
import com.communify.domain.user.dto.UserInfoForSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
interface UserMapper {

    Integer insert(String email, String hashed, String name);

    Optional<UserInfoForLogin> findUserInfoForLoginByEmail(String email);

    Optional<UserInfoForSearch> findUserInfoForSearch(Long userId, Long searcherId);

    Optional<String> findHashed(Long userId);

    void deleteById(Long userId);

    void setToken(String token, Long userId);

    Optional<String> findTokenById(Long userId);

    List<String> findTokensOfFollowers(Long userId);

    void updatePassword(String newHashed, Long userId);

    void incFollowerCount(Long followeeId, Integer count);

    void incFolloweeCount(Long followerId, Integer count);

    void decFollowerCount(Long followeeId, Integer count);

    void decFolloweeCount(Long followerId, Integer count);

    void decFollowerCountOfFollowees(Long followerId, Integer count);

    void decFolloweeCountOfFollowers(Long followeeId, Integer count);

    Optional<String> findTokenOfPostWriter(Long postId);
}
