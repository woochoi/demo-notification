package com.notification.service;

import com.notification.domain.Notification;
import com.notification.repository.NotificationRepository;
import com.notification.service.dto.GetUserNotificationsByPivotResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationListService {

    private final NotificationRepository repository;

    public NotificationListService(NotificationRepository repository) {
        this.repository = repository;
    }

    public GetUserNotificationsByPivotResult getUserNotificationsByPivot(long userId, LocalDateTime occurredAt) {
        Slice<Notification> result;

        if (occurredAt == null) {
            result = repository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0, PAGE_SIZE));
        } else {
            result = repository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }

        return new GetUserNotificationsByPivotResult(
                result.toList(),
                result.hasNext()
        );
    }

    private static final int PAGE_SIZE = 20;
}
