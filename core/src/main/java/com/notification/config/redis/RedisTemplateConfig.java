package com.notification.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Profile("redis") // 컨슈머 모듈에는 필요가 없다!
@Configuration
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        
        // String 으로 저장한다!
        redisTemplate.setKeySerializer(new StringRedisSerializer());    // 직렬화 (문자열...)
        redisTemplate.setValueSerializer(new StringRedisSerializer());  // 직렬화 (문자열...)

        return redisTemplate;
    }
}
