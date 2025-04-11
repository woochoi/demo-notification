package com.notification.consumer;

import com.notification.event.FollowEvent;
import com.notification.task.FollowAddTask;
import com.notification.task.FollowRemoveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.notification.event.FollowEventType.ADD;
import static com.notification.event.FollowEventType.REMOVE;

@Slf4j
@Component
public class FollowEventConsumer {

    private final FollowAddTask followAddTask;

    private final FollowRemoveTask followRemoveTask;

    public FollowEventConsumer(FollowAddTask followAddTask, FollowRemoveTask followRemoveTask) {
        this.followAddTask = followAddTask;
        this.followRemoveTask = followRemoveTask;
    }

    @Bean("follow")
    public Consumer<FollowEvent> follow() {
        return event -> {
            if (event.getType() == ADD) {
                followAddTask.processEvent(event);
            } else if (event.getType() == REMOVE) {
                followRemoveTask.processEvent(event);
            }
        };
    }
}

/*

{
  "type": "ADD",
  "userId": 2,          --> followerId 2 가 팔로워 아아디
  "targetUserId": 1,    --> userId  1 의 알림으로 생성
  "createdAt": "2025-04-10T18:25:43.511Z"
}

{
  "type": "ADD",
  "userId": 3, 번 유저가
  "targetUserId": 1, 번 유저를 팔로워 했다!
  "createdAt": "2025-04-10T18:25:43.511Z"
}
-->

_id :"67f7dabc8f1c0a23aaf6d10b"
followerId : 3
userId :1
type :"FOLLOW"
occurredAt : 2025-04-10T09:25:43.511+00:00
createdAt : 2025-04-10T14:50:36.476+00:00
lastUpdatedAt :2025-04-10T14:50:36.476+00:00
deletedAt : 2025-07-09T14:50:36.476+00:00
_class :"FollowNotification"

 */