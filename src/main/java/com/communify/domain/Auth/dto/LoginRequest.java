package com.communify.domain.Auth.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class LoginRequest {
    private final String email;
    private final String password;
}
