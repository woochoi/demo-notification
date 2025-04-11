package com.notification.controller;

import com.notification.response.UserNotificationListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.Instant;

@Tag(name = "사용자 알림센터 API") // API 묶음
public interface UserNotificationListControllerSpec {

    @Operation(summary = "사용자 알림 목록 조회") // API 설명
    UserNotificationListResponse getNotifications(
            @Parameter(example = "1") long userId,
            @Parameter(example = "2024-01-01T00:11:22.382Z") Instant pivot
    );
}
