package com.communify.domain.push.application;

import com.communify.domain.push.dto.PushRequest;

public interface PushService {

    void push(PushRequest request);
}
