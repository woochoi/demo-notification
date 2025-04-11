package com.notification.domain;

import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;


@Getter
@TypeAlias("CommentNotification")
public class CommentNotification extends Notification {
    private final long postId; // 게시글
    private final long writerId; // 댓글 작성자 아이디 (유저 이름)
    private final String comment; // 댓글 내용
    private final long commentId; // 해당 댓글 아이디

    public CommentNotification(String id, long userId, NotificationType type,
                               Instant occurredAt, Instant createdAt,
                               Instant lastUpdatedAt, Instant deletedAt,
                               long postId, long writerId, String comment,
                               long commentId) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.writerId = writerId;
        this.comment = comment;
        this.commentId = commentId;
    }
}
