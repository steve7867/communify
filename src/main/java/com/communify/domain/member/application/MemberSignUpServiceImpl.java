package com.communify.domain.member.application;

import com.communify.domain.auth.application.AuthService;
import com.communify.domain.auth.error.exception.EmailNotVerifiedException;
import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberSignUpRequest;
import com.communify.domain.member.error.exception.EmailAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSignUpServiceImpl implements MemberSignUpService {

    private final AuthService authService;
    private final MemberRepository memberRepository;

    public void signUp(MemberSignUpRequest request) {
        try {
            boolean isEmailVerified = authService.isEmailVerified(request.getEmail());
            if (!isEmailVerified) {
                throw new EmailNotVerifiedException(request.getEmail());
            }

            memberRepository.insert(request);
        } catch (DuplicateKeyException e) {
            throw new EmailAlreadyUsedException(request.getEmail(), e);
        }
    }
}
