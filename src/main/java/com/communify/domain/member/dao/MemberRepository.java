package com.communify.domain.member.dao;

import com.communify.domain.member.dto.MemberInfoForLogin;
import com.communify.domain.member.dto.MemberInfoForUpdate;
import com.communify.domain.member.dto.MemberInfoForWithdraw;
import com.communify.domain.member.dto.MemberSearchRequest;
import com.communify.domain.member.dto.MemberSignUpRequest;
import com.communify.domain.member.dto.PasswordUpdateRequest;
import com.communify.domain.member.dto.outgoing.MemberInfoForSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberRepository {

    int insert(@Param("request") MemberSignUpRequest request);

    Optional<MemberInfoForLogin> findMemberInfoForLoginByEmail(String email);

    Optional<MemberInfoForSearch> findMemberInfoForSearch(MemberSearchRequest memberId);

    Optional<MemberInfoForUpdate> findMemberInfoForUpdateById(Long memberId);

    Optional<MemberInfoForWithdraw> findMemberInfoForWithdrawById(Long memberId);

    void deleteById(Long memberId);

    void setFcmToken(String fcmToken, Long memberId);

    void updatePassword(@Param("request") PasswordUpdateRequest request);

    void incrementFollowerCount(Long followeeId, Integer count);

    void incrementFolloweeCount(Long followerId, Integer count);

    void decrementFollowerCount(Long followeeId, Integer count);

    void decrementFolloweeCount(Long followerId, Integer count);

    void decrementFollowerCountOfFollowees(Long followerId, Integer count);

    void decrementFolloweeCountOfFollowers(Long followeeId, Integer count);
}
