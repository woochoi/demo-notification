package com.notification.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;


@Getter
@TypeAlias("FollowNotification") // "_class"
public class FollowNotification extends Notification {
    private final long followerId; // 팔로우를 신청한 사람 (팔로워의 아이디)

    public FollowNotification(String id, long userId, NotificationType type,
                              Instant occurredAt, Instant createdAt,
                              Instant lastUpdatedAt, Instant deletedAt,
                              long followerId) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.followerId = followerId;
    }
}
