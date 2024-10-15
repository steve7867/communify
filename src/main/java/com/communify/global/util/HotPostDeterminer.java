package com.communify.global.util;

import com.communify.domain.post.dto.PostOutline;

public final class HotPostDeterminer {

    public static boolean isCandidateForHot(final PostOutline postOutline) {
        return postOutline.getViewCount() > 1000 && postOutline.getLikeCount() > 100;
    }

    private HotPostDeterminer() {
    }
}
