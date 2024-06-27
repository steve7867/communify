package com.communify.global.application;

import com.communify.global.error.exception.MailTransmissionFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public void sendEmail(final String to, final String title, final String text) {
        final SimpleMailMessage emailForm = createEmailForm(to, title, text);
        try {
            emailSender.send(emailForm);
        } catch (MailException e) {
            throw new MailTransmissionFailureException(e);
        }
    }

    private SimpleMailMessage createEmailForm(final String to, final String title, final String text) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}
