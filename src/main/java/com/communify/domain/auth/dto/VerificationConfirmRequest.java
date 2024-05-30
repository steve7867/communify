package com.communify.domain.auth.dto;

public class VerificationConfirmRequest {

    private static final VerificationConfirmRequest instance = new VerificationConfirmRequest();

    private VerificationConfirmRequest() {
    }

    public static VerificationConfirmRequest empty() {
        return instance;
    }
}
