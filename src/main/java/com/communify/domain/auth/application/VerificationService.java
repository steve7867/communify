package com.communify.domain.auth.application;

import com.communify.domain.auth.dto.CodeIssueRequest;

public interface VerificationService {

    void issueVerificationCode(CodeIssueRequest request);

    void verifyCode(String code);

    boolean isVerified(String email);
}
