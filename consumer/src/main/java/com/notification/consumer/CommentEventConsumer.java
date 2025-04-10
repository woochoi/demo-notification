package com.notification.consumer;

import com.notification.event.CommentEvent;
import com.notification.task.CommentAddTask;
import com.notification.task.CommentRemoveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.notification.event.CommentEventType.ADD;
import static com.notification.event.CommentEventType.REMOVE;

@Slf4j
@Component
public class CommentEventConsumer {

    private final CommentAddTask commentAddTask;

    private final CommentRemoveTask commentRemoveTask;

    public CommentEventConsumer(CommentAddTask commentAddTask, CommentRemoveTask commentRemoveTask) {
        this.commentAddTask = commentAddTask;
        this.commentRemoveTask = commentRemoveTask;
    }

    @Bean("comment")
    public Consumer<CommentEvent> comment() {
        return event -> {
            if (event.getType() == ADD) {
                commentAddTask.processEvent(event);
            } else if (event.getType() == REMOVE) {
                commentRemoveTask.processEvent(event);
            }
        };
    }
}
