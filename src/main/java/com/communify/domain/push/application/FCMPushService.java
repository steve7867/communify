package com.communify.domain.push.application;

import com.communify.domain.push.dto.MessageDto;
import com.communify.domain.push.dto.PushRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FCMPushService implements PushService {

    private final FirebaseMessaging messaging;

    @Override
    public void push(final PushRequest request) {
        final String token = request.getToken();
        final MessageDto messageDto = request.getMessageDto();

        final Message message = Message.builder()
                .putData("title", messageDto.getTitle())
                .putData("body", messageDto.getBody())
                .putData("created", LocalDateTime.now().toString())
                .setToken(token)
                .build();

        messaging.sendAsync(message);
    }
}
