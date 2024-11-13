package com.communify.global.config;

import com.communify.global.util.CacheNames;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    @Profile("prod")
    @Configuration
    @NoArgsConstructor
    private static class ConnectionConfigProd {

        @Value("${spring.redis.cache.master.name}")
        private String masterName;

        @Value("${spring.redis.cache.sentinel.node}")
        private List<String> sentinelList;

        @Bean("cacheConnectionFactory")
        public RedisConnectionFactory connectionFactory() {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(masterName);
            sentinelList.forEach(sentinelNode -> {
                String[] parts = sentinelNode.split(":");
                String host = parts[0];
                Integer port = Integer.valueOf(parts[1]);

                config.sentinel(host, port);
            });

            return new LettuceConnectionFactory(config);
        }
    }

    @Profile("dev")
    @Configuration
    @NoArgsConstructor
    private static class ConnectionConfigDev {

        @Value("${spring.redis.cache.host}")
        private String host;

        @Value("${spring.redis.cache.port}")
        private Integer port;

        @Bean("cacheConnectionFactory")
        public RedisConnectionFactory connectionFactoryDev() {
            RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(host, port);
            return new LettuceConnectionFactory(standaloneConfiguration);
        }
    }

    @Bean
    public CacheManager cacheManager(
            @Qualifier("cacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        BasicPolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL);

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));

        RedisCacheConfiguration hotPostOutlinesConfig = defaultCacheConfig.entryTtl(Duration.ofMinutes(1L));
        RedisCacheConfiguration postOutlinesConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(30L));
        RedisCacheConfiguration postDetailConfig = defaultCacheConfig.entryTtl(Duration.ofMinutes(1L));
        RedisCacheConfiguration commentsConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(30L));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withCacheConfiguration(CacheNames.HOT_POST_OUTLINES, hotPostOutlinesConfig)
                .withCacheConfiguration(CacheNames.POST_OUTLINES, postOutlinesConfig)
                .withCacheConfiguration(CacheNames.POST_DETAIL, postDetailConfig)
                .withCacheConfiguration(CacheNames.COMMENTS, commentsConfig)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            @Qualifier("cacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }
}
