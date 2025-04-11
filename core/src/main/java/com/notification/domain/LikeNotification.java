package com.notification.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;
import java.util.List;

@Getter
@TypeAlias("LikeNotification")
public class LikeNotification extends Notification {
    private final long postId; // 게시글
    private final List<Long> likerIds; // 종아요를 누른 사용자 아이디들...

    public LikeNotification(String id, long userId, NotificationType type,
                            Instant occurredAt, Instant createdAt,
                            Instant lastUpdatedAt, Instant deletedAt,
                            long postId, List<Long> likerIds) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.likerIds = likerIds;
    }

    public void addLiker(long likerId, Instant occurredAt, Instant now, Instant retention) {
        this.likerIds.add(likerId); // 추가
        this.setOccurredAt(occurredAt);
        this.setLastUpdatedAt(now);
        this.setDeletedAt(retention);
    }

    public void removeLiker(long userId, Instant now) {
        this.likerIds.remove(userId);
        this.setLastUpdatedAt(now);
    }
}
