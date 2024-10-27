package com.communify.global.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtLeastFor = "3s", defaultLockAtMostFor = "10s")
public class SchedulerConfig {

    @Bean
    public LockProvider lockProvider(
            @Qualifier("cacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        return new RedisLockProvider(redisConnectionFactory);
    }
}
