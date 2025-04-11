package com.notification.controller;

import com.notification.response.UserNotificationListResponse;
import com.notification.service.GetUserNotificationsService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

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
            @RequestParam(value = "pivot", required = false) Instant pivot // 첫번째 호출에는 없으니...
    ) {
        /*
            대용량 비동기 처리 시스템에서는 일반적으로 Instant를 사용하여 이벤트 발생 시점을 정확히 기록하고,
            클라이언트에 표시할 때는 LocalDateTime으로 변환하는 접근 방식이 많이 사용됩니다.

            Instant now = Instant.now();
            Instant ninetyDaysLater = now.plus(Duration.ofDays(90)); // --> 가장 명확하고 안전한 방법
            Instant ninetyDaysLater1 = now.plus(Period.ofDays(90));
        */

        return UserNotificationListResponse.of( // ConvertedNotification 이므로 다시 변환해줘야 한다!
                // List<ConvertedNotification > convertedNotifications
                // GetUserNotificationsResult > (convertedNotifications, result.isHasNext())
                getUserNotificationsService.getUserNotificationsByPivot(userId, pivot)
        );
    }
}
