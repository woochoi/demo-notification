package com.notification.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.LocalDateTime;

@Getter
@TypeAlias("CommentNotification")
public class CommentNotification extends Notification {
    private final long postId;
    private final long writerId;
    private final String comment;
    private final long commentId;

    public CommentNotification(String id, long userId, NotificationType type, LocalDateTime occurredAt, LocalDateTime createdAt,
                               LocalDateTime lastUpdatedAt, LocalDateTime deletedAt, long postId, long writerId, String comment,
                               long commentId) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.writerId = writerId;
        this.comment = comment;
        this.commentId = commentId;
    }
}
