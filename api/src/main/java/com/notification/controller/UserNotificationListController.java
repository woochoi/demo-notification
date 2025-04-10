package com.notification.controller;

import com.notification.response.UserNotificationListResponse;
import com.notification.service.GetUserNotificationsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/user-notifications")
public class UserNotificationListController implements UserNotificationListControllerSpec {

    private final GetUserNotificationsService getUserNotificationsService;

    public UserNotificationListController(GetUserNotificationsService getUserNotificationsService) {
        this.getUserNotificationsService = getUserNotificationsService;
    }

    @Override
    @GetMapping("/{userId}")
    public UserNotificationListResponse getNotifications(
            @PathVariable(value = "userId") long userId,
            @RequestParam(value = "pivot", required = false) LocalDateTime pivot
    ) {
        return UserNotificationListResponse.of(
                getUserNotificationsService.getUserNotificationsByPivot(userId, pivot)
        );
    }
}
