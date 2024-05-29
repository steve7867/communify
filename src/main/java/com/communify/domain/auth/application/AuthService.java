package com.communify.domain.auth.application;

import com.communify.domain.auth.dto.LoginRequest;
import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.application.MailService;
import com.communify.global.util.PasswordEncryptor;
import com.communify.global.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberFindService memberFindService;
    private final SessionService sessionService;
    private final MailService mailService;

    public void login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        MemberInfo memberInfo = memberFindService.findMemberInfoByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        String hashed = memberInfo.getHashed();
        if (!PasswordEncryptor.isMatch(password, hashed)) {
            throw new InvalidPasswordException(password);
        }

        sessionService.add(SessionKey.MEMBER_ID, memberInfo.getId());
        sessionService.add(SessionKey.MEMBER_NAME, memberInfo.getName());
    }

    public void logout() {
        sessionService.invalidate();
    }

    public boolean isLoggedIn() {
        return sessionService.get(SessionKey.MEMBER_ID) != null;
    }

    public void certify(String password, Long memberId) {
        MemberInfo memberInfo = memberFindService.findMemberInfoById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        String hashed = memberInfo.getHashed();

        boolean isMatched = PasswordEncryptor.isMatch(password, hashed);

        if (!isMatched) {
            throw new InvalidPasswordException(password);
        }
    }

    public void publishEmailVerificationCode(String email) {
        String verificationCode = UUID.randomUUID().toString();

        sessionService.add(SessionKey.VERIFICATION_CODE, verificationCode);
        sessionService.add(SessionKey.PUBLICATION_TIME, System.currentTimeMillis());

        mailService.sendEmail(email, "Communify 인증 코드", "인증 코드: " + verificationCode);
    }
}
