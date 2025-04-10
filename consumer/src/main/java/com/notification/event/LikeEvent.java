package com.notification.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeEvent {
    private LikeEventType type;
    private long postId;
    private long userId;
    private LocalDateTime createdAt;
}
