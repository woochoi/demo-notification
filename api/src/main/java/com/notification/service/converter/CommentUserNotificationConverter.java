package com.notification.service.converter;

import com.notification.client.PostClient;
import com.notification.client.UserClient;
import com.notification.domain.CommentNotification;
import com.notification.domain.Post;
import com.notification.domain.User;
import com.notification.service.dto.ConvertedCommentNotification;
import org.springframework.stereotype.Service;

@Service
public class CommentUserNotificationConverter {

    private final UserClient userClient;
    private final PostClient postClient;

    public CommentUserNotificationConverter(UserClient userClient, PostClient postClient) {
        this.userClient = userClient;
        this.postClient = postClient;
    }

    public ConvertedCommentNotification convert(CommentNotification notification) {
        User user = userClient.getUser(notification.getWriterId()); // 알림의 댓글 작성자 아이디
        Post post = postClient.getPost(notification.getPostId());   // 게시물

        return new ConvertedCommentNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),

                user.getName(), // 유저명 (작성자) Notification - CommentNotification > writerId; // 댓글 작성자 아이디 (유저 이름)
                user.getProfileImageUrl(),

                notification.getComment(),
                post.getImageUrl()
        );
    }
}
