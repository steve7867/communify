package com.communify.domain.auth.application;

public interface VerificationService {

    void issueVerificationCode(String email);

    void verifyCode(String code);

    boolean isVerified(String email);
}
