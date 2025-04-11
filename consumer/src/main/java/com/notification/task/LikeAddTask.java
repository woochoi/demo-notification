package com.notification.task;

import com.notification.client.PostClient;
import com.notification.domain.LikeNotification;
import com.notification.domain.Notification;
import com.notification.domain.Post;
import com.notification.event.LikeEvent;
import com.notification.service.NotificationGetService;
import com.notification.service.NotificationSaveService;
import com.notification.utils.NotificationIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.notification.domain.NotificationType.LIKE;

@Slf4j
@Component
public class LikeAddTask {

    private final PostClient postClient;

    private final NotificationGetService getService;

    private final NotificationSaveService saveService;

    public LikeAddTask(PostClient postClient, NotificationGetService getService, NotificationSaveService saveService) {
        this.postClient = postClient;
        this.getService = getService;
        this.saveService = saveService;
    }

    public void processEvent(LikeEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if (post == null) {
            log.error("Post is null with postId:{}", event.getPostId());
            return;
        }

        if (post.getUserId() == event.getUserId()) {
            return;
        }

        saveService.upsert(createOrUpdateNotification(post, event));
    }

    private Notification createOrUpdateNotification(Post post, LikeEvent event) {
        Optional<Notification> optionalNotification
                = getService.getNotificationByTypeAndPostId(LIKE, post.getId());

        Instant now = Instant.now();
        Instant retention = now.plus(Duration.ofDays(90));

        if (optionalNotification.isPresent()) {
            return updateNotification((LikeNotification) optionalNotification.get(), event, now, retention);
        } else {
            return createNotification(post, event, now, retention);
        }
    }

    private Notification updateNotification(LikeNotification notification, LikeEvent event, Instant now, Instant retention) {
        notification.addLiker(event.getUserId(), event.getCreatedAt(), now, retention);
        return notification;
    }

    private Notification createNotification(Post post, LikeEvent event, Instant now, Instant retention) {
        return new LikeNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                LIKE,
                event.getCreatedAt(),
                now,
                now,
                retention,
                post.getId(),
                List.of(event.getUserId())
        );
    }
}
