package com.notification.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.Duration;

@Repository
public class NotificationReadRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public NotificationReadRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 유저의 읽음 시간 저장
     */
    public Instant setLastReadAt(long userId) {
        long lastReadAt = Instant.now().toEpochMilli(); // 마지막으로 읽은 시간

        String key = getKey(userId); // "1:lastReadAt"

        redisTemplate.opsForValue().set(key, String.valueOf(lastReadAt)); // 키값과 벨류값 저장
        // 값을 저장할 때 만료 시간도 함께 설정
        //redisTemplate.opsForValue().set(key, value, Duration.ofDays(90));

        redisTemplate.expire(key, Duration.ofDays(90));     // TTL

        return Instant.ofEpochMilli(lastReadAt); // long -> Instant 변경
    }

    public Instant getLastReadAt(long userId) {
        String key = getKey(userId);
        String lastReadAtStr = redisTemplate.opsForValue().get(key);

        if (lastReadAtStr == null) {
            return null;
        }

        long lastReadAtLong = Long.parseLong(lastReadAtStr); // redis 에 "1744381916200" 저장

        return Instant.ofEpochMilli(lastReadAtLong);
    }

    private String getKey(long userId) {
        return userId + ":lastReadAt";
    }
}
