package com.communify.domain.verification.application;

import com.communify.domain.verification.dto.CodeIssueRequest;
import com.communify.domain.verification.dto.VerificationConfirmRequest;
import com.communify.domain.verification.dto.VerificationRequest;

public interface VerificationService {

    void issueVerificationCode(CodeIssueRequest request);

    void verifyCode(VerificationRequest request);

    boolean isVerified(VerificationConfirmRequest request);
}
