package com.notification.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@TypeAlias("LikeNotification")
public class LikeNotification extends Notification {
    private final long postId;
    private final List<Long> likerIds;

    public LikeNotification(String id, long userId, NotificationType type, LocalDateTime occurredAt, LocalDateTime createdAt,
                            LocalDateTime lastUpdatedAt, LocalDateTime deletedAt, long postId, List<Long> likerIds) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.likerIds = likerIds;
    }

    public void addLiker(long likerId, LocalDateTime occurredAt, LocalDateTime now, LocalDateTime retention) {
        this.likerIds.add(likerId);
        this.setOccurredAt(occurredAt);
        this.setLastUpdatedAt(now);
        this.setDeletedAt(retention);
    }

    public void removeLiker(long userId, LocalDateTime now) {
        this.likerIds.remove(userId);
        this.setLastUpdatedAt(now);
    }
}
