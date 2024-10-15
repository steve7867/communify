package com.communify.domain.push;

import com.communify.domain.push.dto.PushInfo;

public interface PushService {

    boolean push(PushInfo info);
}
