package com.communify.domain.verification.application;

import com.communify.domain.verification.dto.CodeIssueRequest;
import com.communify.domain.verification.dto.VerificationConfirmRequest;
import com.communify.domain.verification.dto.VerificationRequest;
import com.communify.domain.verification.error.exception.VerificationCodeNotEqualException;
import com.communify.domain.verification.error.exception.VerificationCodeNotPublishedException;
import com.communify.domain.verification.error.exception.VerificationTimeOutException;
import com.communify.global.application.MailService;
import com.communify.global.application.SessionService;
import com.communify.global.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailBasedVerificationService implements VerificationService {

    private final SessionService sessionService;
    private final MailService mailService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private Long expirationTime;

    @Override
    public void issueVerificationCode(CodeIssueRequest request) {
        String email = request.getEmail();

        String verificationCode = UUID.randomUUID().toString().substring(0, 8);

        mailService.sendEmail(email, "Communify 인증 코드", "인증 코드: " + verificationCode);

        sessionService.add(SessionKey.VERIFICATION_CODE, verificationCode);
        sessionService.add(SessionKey.ISSUE_TIME, System.currentTimeMillis());
    }

    @Override
    public void verifyCode(VerificationRequest request) {
        String code = request.getCode();

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

    @Override
    public boolean isVerified(VerificationConfirmRequest request) {
        return sessionService.get(SessionKey.EMAIL_VERIFIED).isPresent();
    }
}
