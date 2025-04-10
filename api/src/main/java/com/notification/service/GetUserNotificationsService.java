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

import java.time.LocalDateTime;
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

    public GetUserNotificationsResult getUserNotificationsByPivot(long userId, LocalDateTime pivot) {
        GetUserNotificationsByPivotResult result = listService.getUserNotificationsByPivot(userId, pivot);

        List<ConvertedNotification> convertedNotifications = result.getNotifications().stream()
                .map(notification -> switch (notification.getType()) {
                    case COMMENT -> commentConverter.convert((CommentNotification) notification);
                    case LIKE -> likeConverter.convert((LikeNotification) notification);
                    case FOLLOW -> followConverter.convert((FollowNotification) notification);
                })
                .toList();

        return new GetUserNotificationsResult(
               convertedNotifications,
               result.isHasNext()
        );
    }
}
