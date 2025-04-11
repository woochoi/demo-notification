package com.notification.config.redis;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

//@Profile("test")
@Profile("redis") // profile 이 redis 경우만 빈으로 관리 > redis 는 API 모듈(실행)에만 적용되어야 한다
@Slf4j
@Configuration
public class LocalRedisConfig {

    private static final int PORT = 6379;

    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(
                        "localhost",
                        PORT
                );
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

}
