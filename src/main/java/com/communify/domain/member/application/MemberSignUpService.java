package com.communify.domain.member.application;

import com.communify.domain.member.dto.incoming.MemberSignUpRequest;

public interface MemberSignUpService {

    void signUp(MemberSignUpRequest request);
}
