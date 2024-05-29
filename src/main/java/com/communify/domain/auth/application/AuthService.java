package com.communify.domain.auth.application;

import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.application.MailService;
import com.communify.global.application.SessionService;
import com.communify.global.util.PasswordEncryptor;
import com.communify.global.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberFindService memberFindService;
    private final SessionService sessionService;
    private final MailService mailService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private Long expirationTime;

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

    public boolean verify(String code) {
        String verificationCode = (String) sessionService.get(SessionKey.VERIFICATION_CODE);
        Long publicationTime = (Long) sessionService.get(SessionKey.PUBLICATION_TIME);

        if (!Objects.equals(code, verificationCode) || isTimeOut(publicationTime)) {
            return false;
        }

        sessionService.remove(SessionKey.VERIFICATION_CODE);
        sessionService.remove(SessionKey.PUBLICATION_TIME);
        sessionService.add(SessionKey.EMAIL_VERIFIED, true);

        return true;
    }

    private boolean isTimeOut(Long publicationTime) {
        return System.currentTimeMillis() - publicationTime > expirationTime;
    }

    public boolean isEmailVerified(String email) {
        Boolean isEmailVerified = (Boolean) sessionService.get(SessionKey.EMAIL_VERIFIED);
        return isEmailVerified == null ? false : isEmailVerified;
    }
}
