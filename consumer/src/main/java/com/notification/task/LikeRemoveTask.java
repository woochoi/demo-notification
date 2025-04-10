package com.notification.task;

import com.notification.domain.LikeNotification;
import com.notification.domain.Notification;
import com.notification.event.LikeEvent;
import com.notification.service.NotificationGetService;
import com.notification.service.NotificationRemoveService;
import com.notification.service.NotificationSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.notification.domain.NotificationType.LIKE;

@Slf4j
@Component
public class LikeRemoveTask {

    private final NotificationGetService getService;

    private final NotificationRemoveService removeService;

    private final NotificationSaveService saveService;

    public LikeRemoveTask(NotificationGetService getService, NotificationRemoveService removeService, NotificationSaveService saveService) {
        this.getService = getService;
        this.removeService = removeService;
        this.saveService = saveService;
    }

    public void processEvent(LikeEvent event) {
        Optional<Notification> optionalNotification
                = getService.getNotificationByTypeAndPostId(LIKE, event.getPostId());
        if (optionalNotification.isEmpty()) {
            log.error("No notification with postId: {}", event.getPostId());
            return;
        }

        LikeNotification notification = (LikeNotification) optionalNotification.get();
        removeLikerAndUpdateNotification(notification, event);
    }

    private void removeLikerAndUpdateNotification(LikeNotification notification, LikeEvent event) {
        notification.removeLiker(event.getUserId(), LocalDateTime.now());

        if (notification.getLikerIds().isEmpty()) {
            removeService.deleteById(notification.getId());
        } else {
            saveService.upsert(notification);
        }
    }
}
