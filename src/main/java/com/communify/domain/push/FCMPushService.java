package com.communify.domain.push;

import com.communify.domain.push.dto.MessageDto;
import com.communify.domain.push.dto.PushInfo;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    private Message buildMessage(final MessageDto dto) {
        if (dto.getBody() != null) {
            return Message.builder()
                    .putData("title", dto.getTitle())
                    .putData("created", LocalDateTime.now().toString())
                    .putData("body", dto.getBody())
                    .setToken(dto.getToken())
                    .build();
        }

        return Message.builder()
                .putData("title", dto.getTitle())
                .putData("created", LocalDateTime.now().toString())
                .setToken(dto.getToken())
                .build();
    }
}
