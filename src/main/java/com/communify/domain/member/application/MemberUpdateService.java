package com.communify.domain.member.application;

import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.member.dao.MemberRepository;
import com.communify.domain.member.dto.MemberInfoForUpdate;
import com.communify.domain.member.dto.PasswordUpdateRequest;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.util.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository memberRepository;

    public void updatePassword(final PasswordUpdateRequest request) {
        final Long memberId = request.getMemberId();

        final MemberInfoForUpdate memberInfo = memberRepository.findMemberInfoForUpdateById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        final String currentPassword = request.getCurrentPassword();
        if (!PasswordEncryptor.isMatch(currentPassword, memberInfo.getHashed())) {
            throw new InvalidPasswordException(currentPassword);
        }

        memberRepository.updatePassword(request);
    }

    public void setToken(final Long memberId, final String token) {
        memberRepository.setToken(memberId, token);
    }
}
