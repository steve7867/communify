package com.communify.domain.member.application;

import com.communify.domain.member.dto.MemberSignUpRequest;

public interface MemberSignUpService {

    void signUp(MemberSignUpRequest request);
}
