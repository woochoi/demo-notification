package com.notification.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * core 에서 사용과 동일...
 * package com.notification.service.dto;
 * GetUserNotificationsByPivotResult
 */
@Getter
@AllArgsConstructor
public class GetUserNotificationsResult { // hasNext 값도 같이 내려줘야 하기 때문
    private List<ConvertedNotification> notifications;
    private boolean hasNext;
}
