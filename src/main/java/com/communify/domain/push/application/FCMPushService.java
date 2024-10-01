package com.communify.domain.push.application;

import com.communify.domain.push.dto.MessageDto;
import com.communify.domain.push.dto.PushInfo;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
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
    public boolean push(final PushInfo info) {
        if (!info.isPushable()) {
            return false;
        }

        final Message message = buildMessage(info.getMessageDto());

        try {
            messaging.send(message);
        } catch (FirebaseMessagingException e) {
            return false;
        }

        return true;
    }

    private Message buildMessage(final MessageDto messageDto) {
        if (Objects.nonNull(messageDto.getBody())) {
            return Message.builder()
                    .putData("title", messageDto.getTitle())
                    .putData("created", LocalDateTime.now().toString())
                    .putData("body", messageDto.getBody())
                    .setToken(messageDto.getToken())
                    .build();
        }

        return Message.builder()
                .putData("title", messageDto.getTitle())
                .putData("created", LocalDateTime.now().toString())
                .setToken(messageDto.getToken())
                .build();
    }
}
