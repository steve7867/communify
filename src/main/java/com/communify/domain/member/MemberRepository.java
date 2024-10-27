package com.communify.domain.member;

import com.communify.domain.member.dto.MemberInfoForLogin;
import com.communify.domain.member.dto.MemberInfoForSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {

    Integer insert(String email, String hashed, String name);

    Optional<MemberInfoForLogin> findMemberInfoForLoginByEmail(String email);

    Optional<MemberInfoForSearch> findMemberInfoForSearch(Long memberId, Long searcherId);

    Optional<String> findHashed(Long memberId);

    void deleteById(Long memberId);

    void setToken(String token, Long memberId);

    Optional<String> findTokenById(Long memberId);

    List<String> findTokensOfFollowers(Long memberId);

    void updatePassword(String newHashed, Long memberId);

    void incFollowerCount(Long followeeId, Integer count);

    void incFolloweeCount(Long followerId, Integer count);

    void decFollowerCount(Long followeeId, Integer count);

    void decFolloweeCount(Long followerId, Integer count);

    void decFollowerCountOfFollowees(Long followerId, Integer count);

    void decFolloweeCountOfFollowers(Long followeeId, Integer count);

    Optional<String> findTokenOfPostWriter(Long postId);
}
