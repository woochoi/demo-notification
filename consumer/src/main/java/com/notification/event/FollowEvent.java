package com.notification.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowEvent {
    private FollowEventType type;
    private long userId;
    private long targetUserId;
    private LocalDateTime createdAt;
}
