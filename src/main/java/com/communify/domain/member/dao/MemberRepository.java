package com.communify.domain.member.dao;

import com.communify.domain.member.dto.MemberSignUpRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberRepository {

    int insert(MemberSignUpRequest request);
}
