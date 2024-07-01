package com.communify.domain.member.dao;

import com.communify.domain.member.dto.PasswordUpdateRequest;
import com.communify.domain.member.dto.incoming.MemberSignUpRequest;
import com.communify.domain.member.dto.outgoing.MemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberRepository {

    int insert(@Param("request") MemberSignUpRequest request);

    Optional<MemberInfo> findByEmail(String email);

    Optional<MemberInfo> findById(Long memberId);

    void deleteById(Long memberId);

    void setFcmToken(String fcmToken, Long memberId);

    Optional<String> findFcmTokenById(Long memberId);

    void updatePassword(@Param("request") PasswordUpdateRequest request);

    void incrementFollowerCount(Long followeeId, Integer count);

    void incrementFolloweeCount(Long followerId, Integer count);

    void decrementFollowerCount(Long followeeId, Integer count);

    void decrementFolloweeCount(Long followerId, Integer count);

    void decrementFollowerCountOfFollowees(Long followerId, Integer count);

    void decrementFolloweeCountOfFollowers(Long followeeId, Integer count);
}
