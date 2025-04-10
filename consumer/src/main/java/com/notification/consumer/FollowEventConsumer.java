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
