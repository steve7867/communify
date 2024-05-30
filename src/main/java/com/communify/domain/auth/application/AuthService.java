package com.communify.domain.auth.application;

import com.communify.domain.auth.error.exception.InvalidPasswordException;
import com.communify.domain.auth.error.exception.VerificationCodeNotEqualException;
import com.communify.domain.auth.error.exception.VerificationCodeNotPublishedException;
import com.communify.domain.auth.error.exception.VerificationTimeOutException;
import com.communify.domain.member.application.MemberFindService;
import com.communify.domain.member.dto.MemberInfo;
import com.communify.domain.member.error.exception.EmailAlreadyUsedException;
import com.communify.domain.member.error.exception.MemberNotFoundException;
import com.communify.global.application.MailService;
import com.communify.global.application.SessionService;
import com.communify.global.util.PasswordEncryptor;
import com.communify.global.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
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

    public void issueEmailVerificationCode(String email) {
        Optional<MemberInfo> memberInfoOpt = memberFindService.findMemberInfoByEmail(email);
        if (memberInfoOpt.isPresent()) {
            throw new EmailAlreadyUsedException(email);
        }

        String verificationCode = UUID.randomUUID().toString().substring(0, 8);

        sessionService.add(SessionKey.VERIFICATION_CODE, verificationCode);
        sessionService.add(SessionKey.ISSUE_TIME, System.currentTimeMillis());

        mailService.sendEmail(email, "Communify 인증 코드", "인증 코드: " + verificationCode);
    }

    public void verify(String code) {
        String verificationCode = (String) sessionService.get(SessionKey.VERIFICATION_CODE)
                .orElseThrow(VerificationCodeNotPublishedException::new);
        Long publicationTime = (Long) sessionService.get(SessionKey.ISSUE_TIME).get();

        if (isTimeOut(publicationTime)) {
            throw new VerificationTimeOutException();
        }

        if (!Objects.equals(code, verificationCode)) {
            throw new VerificationCodeNotEqualException();
        }

        sessionService.remove(SessionKey.VERIFICATION_CODE);
        sessionService.remove(SessionKey.ISSUE_TIME);
        sessionService.add(SessionKey.EMAIL_VERIFIED, true);
    }

    private boolean isTimeOut(Long issueTime) {
        return System.currentTimeMillis() - issueTime > expirationTime;
    }

    public boolean isEmailVerified(String email) {
        return sessionService.get(SessionKey.EMAIL_VERIFIED).isPresent();
    }
}
