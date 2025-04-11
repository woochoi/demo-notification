package com.notification.task;

import com.notification.domain.FollowNotification;
import com.notification.event.FollowEvent;
import com.notification.service.NotificationSaveService;
import com.notification.utils.NotificationIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


import static com.notification.domain.NotificationType.FOLLOW;

@Slf4j
@Component
public class FollowAddTask {

    private final NotificationSaveService saveService;

    public FollowAddTask(NotificationSaveService saveService) {
        this.saveService = saveService;
    }

    public void processEvent(FollowEvent event) {
        if (event.getTargetUserId() == event.getUserId()) {
            log.error("targetUserId and userId cannot be the same");
            return;
        }

        saveService.insert(createFollowNotification(event));
    }

    private FollowNotification createFollowNotification(FollowEvent event) { // Notification
        LocalDateTime now = LocalDateTime.now();

        return new FollowNotification(
                NotificationIdGenerator.generate(),
                event.getTargetUserId(), // 이벤트를 받을 사람
                FOLLOW,
                event.getCreatedAt(), // 이벤트의 생성시간
                now,
                now,
                now.plusDays(90),
                event.getUserId() // 이벤트를 발생 시킨 사람, 팔로워를 신청한 사람
        );
    }
}
