package com.notification.service.dto;

import com.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetUserNotificationsByPivotResult { // hasNext 값도 같이 내려줘야 하기 때문
    private List<Notification> notifications;
    private boolean hasNext;
}
