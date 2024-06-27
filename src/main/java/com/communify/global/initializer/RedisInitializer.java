package com.communify.global.initializer;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@Slf4j
public class RedisInitializer {

    private final RedisConnectionFactory sessionConnectionFactory;
    private final RedisConnectionFactory cacheConnectionFactory;

    public RedisInitializer(@Qualifier("sessionConnectionFactory") final RedisConnectionFactory sessionConnectionFactory,
                            @Qualifier("cacheConnectionFactory") final RedisConnectionFactory cacheConnectionFactory) {

        this.sessionConnectionFactory = sessionConnectionFactory;
        this.cacheConnectionFactory = cacheConnectionFactory;
    }

    @PostConstruct
    public void flushSessionRedis() {
        log.info("Flushing Session Redis");

        final RedisConnection connection = sessionConnectionFactory.getConnection();
        final RedisServerCommands redisServerCommands = connection.serverCommands();
        redisServerCommands.flushAll();
    }

    @PostConstruct
    public void flushCacheRedis() {
        log.info("Flushing Cache Redis");

        final RedisConnection connection = cacheConnectionFactory.getConnection();
        final RedisServerCommands redisServerCommands = connection.serverCommands();
        redisServerCommands.flushAll();
    }
}
