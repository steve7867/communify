package com.communify.global.config;

import com.communify.global.util.CacheNames;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${spring.redis.cache.host}")
    private String host;

    @Value("${spring.redis.cache.port}")
    private Integer port;

    @Bean("cacheConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(standaloneConfiguration);
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

        RedisCacheConfiguration hotPostOutlinesConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(20L));
        RedisCacheConfiguration postOutlinesConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(10L));
        RedisCacheConfiguration postDetailConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(20L));
        RedisCacheConfiguration commentsConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(10L));
        RedisCacheConfiguration tokenConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(30L));
        RedisCacheConfiguration postWriterIdConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(20L));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withCacheConfiguration(CacheNames.HOT_POST_OUTLINES, hotPostOutlinesConfig)
                .withCacheConfiguration(CacheNames.POST_OUTLINES, postOutlinesConfig)
                .withCacheConfiguration(CacheNames.POST_DETAIL, postDetailConfig)
                .withCacheConfiguration(CacheNames.COMMENTS, commentsConfig)
                .withCacheConfiguration(CacheNames.TOKEN, tokenConfig)
                .withCacheConfiguration(CacheNames.POST_WRITER_ID, postWriterIdConfig)
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
