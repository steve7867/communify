package com.communify.domain.member.dao;

import com.communify.domain.member.dto.outgoing.MemberInfo;
import com.communify.domain.member.dto.incoming.MemberSignUpRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {

    int insert(MemberSignUpRequest request);

    Optional<MemberInfo> findByEmail(String email);

    Optional<MemberInfo> findById(Long memberId);

    void deleteById(Long memberId);

    void setFcmToken(String fcmToken, Long memberId);

    Optional<String> findFcmTokenById(Long memberId);
}
