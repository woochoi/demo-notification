package com.notification.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDateTime;

@Getter
@TypeAlias("FollowNotification")
public class FollowNotification extends Notification {
    private final long followerId;

    public FollowNotification(String id, long userId, NotificationType type, LocalDateTime occurredAt, LocalDateTime createdAt,
                              LocalDateTime lastUpdatedAt, LocalDateTime deletedAt, long followerId) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.followerId = followerId;
    }
}
