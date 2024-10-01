package com.communify.global.util;

public final class CacheKeyUtil {

    private CacheKeyUtil() {
    }

    public static String makeCacheKey(final String cacheName, final Object keyId) {
        return cacheName + "::" + keyId;
    }

    public static String extractKeyId(final String cacheKey) {
        final int i = cacheKey.lastIndexOf("::");
        return cacheKey.substring(i + 2);
    }
}
