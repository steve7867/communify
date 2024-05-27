package com.communify.domain.push.application;

import com.communify.domain.push.dto.MessageDto;

public interface PushService {

    void push(String token, MessageDto messageDto);
}
