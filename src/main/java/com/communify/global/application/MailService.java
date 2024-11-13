package com.communify.global.application;

import com.communify.global.error.exception.MailSendFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String to, String title, String text) {
        SimpleMailMessage message = createMessage(to, title, text);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            throw new MailSendFailException(e);
        }
    }

    private SimpleMailMessage createMessage(String to, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}
