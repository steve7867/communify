package com.communify.domain.push.application;

import com.communify.domain.push.dto.MessageDto;
import com.communify.domain.push.dto.PushRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FCMPushService implements PushService {

    private final FirebaseMessaging messaging;

    @Override
    public void push(final PushRequest request) {
        final Message message = buildMessage(request);
        messaging.sendAsync(message);
    }

    private Message buildMessage(final PushRequest request) {
        final MessageDto messageDto = request.getMessageDto();

        if (Objects.isNull(messageDto.getBody())) {
            return Message.builder()
                    .putData("title", messageDto.getTitle())
                    .putData("created", LocalDateTime.now().toString())
                    .setToken(request.getToken())
                    .build();
        }

        return Message.builder()
                .putData("title", messageDto.getTitle())
                .putData("body", messageDto.getBody())
                .putData("created", LocalDateTime.now().toString())
                .setToken(request.getToken())
                .build();
    }
}
