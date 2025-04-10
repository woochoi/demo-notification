package com.notification.service.dto;

import com.notification.domain.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public abstract class ConvertedNotification {
    protected String id;
    protected NotificationType type;
    protected Instant occurredAt;
    protected Instant lastUpdatedAt;
}
