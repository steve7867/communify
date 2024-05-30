package com.communify.domain.auth.application;

import com.communify.domain.auth.dto.CodeIssueRequest;
import com.communify.domain.auth.dto.VerificationRequest;

public interface VerificationService {

    void issueVerificationCode(CodeIssueRequest request);

    void verifyCode(VerificationRequest request);

    boolean isVerified(String email);
}
