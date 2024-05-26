package com.communify.domain.member.dao;

import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.dto.MemberSignUpRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface MemberRepository {

    int insert(MemberSignUpRequest request);

    Optional<MemberInfo> findByEmail(String email);

    Optional<MemberInfo> findById(Long memberId);

    void deleteById(Long memberId);

    void setFcmToken(String fcmToken, Long memberId);
}
