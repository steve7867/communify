package com.communify.global.util;

public final class CacheKeyUtil {

    private CacheKeyUtil() {
    }

    public static String makeCacheKey(String cacheName, Object keyId) {
        return cacheName + "::" + keyId;
    }

    public static String extractKeyId(String cacheKey) {
        int i = cacheKey.lastIndexOf("::");
        return cacheKey.substring(i + 2);
    }
}
