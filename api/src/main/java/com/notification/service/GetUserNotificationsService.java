package com.notification.service;

import com.notification.domain.CommentNotification;
import com.notification.domain.FollowNotification;
import com.notification.domain.LikeNotification;
import com.notification.service.converter.CommentUserNotificationConverter;
import com.notification.service.converter.FollowUserNotificationConverter;
import com.notification.service.converter.LikeUserNotificationConverter;
import com.notification.service.dto.ConvertedNotification;
import com.notification.service.dto.GetUserNotificationsByPivotResult;
import com.notification.service.dto.GetUserNotificationsResult;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class GetUserNotificationsService {

    private final NotificationListService listService;
    private final CommentUserNotificationConverter commentConverter;
    private final LikeUserNotificationConverter likeConverter;
    private final FollowUserNotificationConverter followConverter;

    public GetUserNotificationsService(NotificationListService listService,
                                       CommentUserNotificationConverter commentConverter,
                                       LikeUserNotificationConverter likeConverter, FollowUserNotificationConverter followConverter) {
        this.listService = listService;
        this.commentConverter = commentConverter;
        this.likeConverter = likeConverter;
        this.followConverter = followConverter;
    }

    public GetUserNotificationsResult getUserNotificationsByPivot(long userId, Instant pivot) {

        // 1. 알림 목록 조회 (core > GetUserNotificationsByPivotResult)
        GetUserNotificationsByPivotResult result = listService.getUserNotificationsByPivot(userId, pivot);

        // 2. 알림 목록을 순환하면서 알림(디비에 저장된) --> 사용자 알림으로 변환
        // (List<Notification> notifications : List 를 분해해서 (stream)... map 로 매핑하고 다시 List 로)
        List<ConvertedNotification> convertedNotifications = result.getNotifications().stream()
                .map(notification -> switch (notification.getType()) {
                    case COMMENT -> commentConverter.convert((CommentNotification) notification);
                    case LIKE -> likeConverter.convert((LikeNotification) notification);
                    case FOLLOW -> followConverter.convert((FollowNotification) notification);
                })
                .toList(); // 다시 리스트로 변경

        return new GetUserNotificationsResult( // hasNext 값도 같이 내려줘야 하기 때문 == GetUserNotificationsByPivotResult
               convertedNotifications,
               result.isHasNext()
        );
    }
}
