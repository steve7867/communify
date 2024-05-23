package com.communify.domain.Auth.application;

import com.communify.domain.member.dto.MemberInfo;

public interface SessionService {

    void login(MemberInfo memberInfo);

    void logout();

    boolean isLoggedIn();

    Object getValue(String sessionKey);
}
