package com.notification.service;

import org.springframework.stereotype.Component;

import java.time.Instant;


/**
 * 신규 알림이 있는지 체크
 */
@Component
public class CheckNewNotificationService {

    private final NotificationGetService notificationGetService;
    private final LastReadAtService lastReadAtService;

    public CheckNewNotificationService(NotificationGetService notificationGetService, LastReadAtService lastReadAtService) {
        this.notificationGetService = notificationGetService;
        this.lastReadAtService = lastReadAtService;
    }

    public boolean checkNewNotification(long userId) {
        // Redis애 저장한 lastReadAt 과 latestUpdatedAt (내가 가진 알림들 중에서 updatedAt 가장 큰 알림, 가장 최신으로 업데이트 된 시간) 두 값을 비교

        Instant latestUpdatedAt = notificationGetService.getLatestUpdatedAt(userId);
        if (latestUpdatedAt == null) {
            return false;
        }

        Instant lastReadAt = lastReadAtService.getLastReadAt(userId);
        if (lastReadAt == null) {
            return true;
        }

        // latestUpdatedAt > lastReadAt, 새로은 알림이 있다
        return latestUpdatedAt.isAfter(lastReadAt);
    }
}
