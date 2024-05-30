package com.communify.domain.auth.application;

import com.communify.domain.auth.dto.verification.CodeIssueRequest;
import com.communify.domain.auth.dto.verification.VerificationConfirmRequest;
import com.communify.domain.auth.dto.verification.VerificationRequest;

public interface VerificationService {

    void issueVerificationCode(CodeIssueRequest request);

    void verifyCode(VerificationRequest request);

    boolean isVerified(VerificationConfirmRequest request);
}
