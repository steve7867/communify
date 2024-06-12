package com.communify.domain.member.dto;

import com.communify.global.util.PasswordEncryptor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordUpdateRequest {

    private final Long memberId;
    private final String currentPassword;
    private final String newPassword;
    private final String newHashed;

    @Builder
    private PasswordUpdateRequest(Long memberId, String currentPassword, String newPassword) {
        this.memberId = memberId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;

        this.newHashed = PasswordEncryptor.encrypt(newPassword);
    }
}
