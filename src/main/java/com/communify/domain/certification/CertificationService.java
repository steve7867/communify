package com.communify.domain.certification;

import com.communify.domain.certification.exception.CertificationCodeNotEqualException;
import com.communify.domain.certification.exception.CertificationCodeNotPublishedException;
import com.communify.domain.certification.exception.CertificationTimeOutException;
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
public class CertificationService {

    private final SessionService sessionService;
    private final MailService mailService;

    @Value("${spring.mail.certification-code-expiration-millis}")
    private Long expirationTime;

    public void issueCode(final String email) {
        final String certificationCode = UUID.randomUUID().toString().substring(0, 8);

        mailService.sendEmail(email, "Communify 인증 코드", "인증 코드: " + certificationCode);

        sessionService.add(SessionKey.ISSUED_CODE, certificationCode);
        sessionService.add(SessionKey.ISSUE_TIME, System.currentTimeMillis());
    }

    public void certifyCode(final String code) {
        final String issuedCode = (String) sessionService.get(SessionKey.ISSUED_CODE)
                .orElseThrow(CertificationCodeNotPublishedException::new);

        final Long issuedTime = (Long) sessionService.get(SessionKey.ISSUE_TIME).get();

        if (isTimeOut(issuedTime)) {
            throw new CertificationTimeOutException();
        }

        if (!Objects.equals(code, issuedCode)) {
            throw new CertificationCodeNotEqualException();
        }

        sessionService.remove(SessionKey.ISSUED_CODE);
        sessionService.remove(SessionKey.ISSUE_TIME);
        sessionService.add(SessionKey.EMAIL_CERTIFIED, true);
    }

    private boolean isTimeOut(final Long issuedTime) {
        return System.currentTimeMillis() - issuedTime > expirationTime;
    }

    public boolean isCertified() {
        return sessionService.get(SessionKey.EMAIL_CERTIFIED).isPresent();
    }
}
