package com.notification.task;

import com.notification.client.CommentClient;
import com.notification.client.PostClient;
import com.notification.domain.*;
import com.notification.event.CommentEvent;
import com.notification.service.NotificationSaveService;
import com.notification.utils.NotificationIdGenerator;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Component
public class CommentAddTask {

    private final PostClient postClient;

    private final CommentClient commentClient;

    private final NotificationSaveService saveService;

    public CommentAddTask(PostClient postClient, CommentClient commentClient, NotificationSaveService saveService) {
        this.postClient = postClient;
        this.commentClient = commentClient;
        this.saveService = saveService;
    }

    public void processEvent(CommentEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if (Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        Comment comment = commentClient.getComment(event.getCommentId());

        Notification notification = createNotification(post, comment);
        saveService.insert(notification);
    }

    private Notification createNotification(Post post, Comment comment) {
        Instant now = Instant.now();

        return new CommentNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                NotificationType.COMMENT,
                comment.getCreatedAt(),
                now,
                now,
                now.plus(Duration.ofDays(90)),
                post.getId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getId()
        );
    }
}
