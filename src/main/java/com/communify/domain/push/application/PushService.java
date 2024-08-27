package com.communify.domain.push.application;

import com.communify.domain.push.dto.PushInfo;

public interface PushService {

    boolean push(PushInfo info);
}
