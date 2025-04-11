package com.notification.service.converter;

import com.notification.client.UserClient;
import com.notification.domain.FollowNotification;
import com.notification.domain.User;
import com.notification.service.dto.ConvertedFollowNotification;
import org.springframework.stereotype.Service;

@Service
public class FollowUserNotificationConverter {

    private final UserClient userClient;

    public FollowUserNotificationConverter(UserClient userClient) {
        this.userClient = userClient;
    }

    public ConvertedFollowNotification convert(FollowNotification notification) {
        User user = userClient.getUser(notification.getFollowerId());

        // User
        boolean isFollowing = userClient.getIsFollowing(notification.getUserId(), notification.getFollowerId());

        return new ConvertedFollowNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),

                isFollowing
        );
    }
}
