package com.notification.service.converter;

import com.notification.client.PostClient;
import com.notification.client.UserClient;
import com.notification.domain.LikeNotification;
import com.notification.domain.Post;
import com.notification.domain.User;
import com.notification.service.dto.ConvertedLikeNotification;
import org.springframework.stereotype.Service;

@Service
public class LikeUserNotificationConverter {

    private final UserClient userClient;
    private final PostClient postClient;

    public LikeUserNotificationConverter(UserClient userClient, PostClient postClient) {
        this.userClient = userClient;
        this.postClient = postClient;
    }

    public ConvertedLikeNotification convert(LikeNotification notification) {
        User user = userClient.getUser(notification.getLikerIds().getLast());
        Post post = postClient.getPost(notification.getPostId());

        return new ConvertedLikeNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                notification.getLikerIds().size(),
                post.getImageUrl()
        );
    }
}
