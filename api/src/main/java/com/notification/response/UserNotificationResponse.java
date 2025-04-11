package com.notification.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.notification.domain.NotificationType;
import com.notification.service.dto.ConvertedCommentNotification;
import com.notification.service.dto.ConvertedFollowNotification;
import com.notification.service.dto.ConvertedLikeNotification;
import com.notification.service.dto.ConvertedNotification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;


/**
 * CommentUserNotificationResponse
 * LikeUserNotificationResponse
 * FollowUserNotificationResponse
 */


@Getter
@AllArgsConstructor
@Schema(description = "유저 알림 응답")
@JsonSubTypes({ // 추상 클래스이므로 서브 클래스의 타입들도 넣어주면 스웨거 문서에 추가된다
        @JsonSubTypes.Type(value = CommentUserNotificationResponse.class),
        @JsonSubTypes.Type(value = LikeUserNotificationResponse.class),
        @JsonSubTypes.Type(value = FollowUserNotificationResponse.class)
})
public abstract class UserNotificationResponse {

    @Schema(description = "알림 ID")
    private String id;

    @Schema(description = "알림 타입")
    private NotificationType type;

    @Schema(description = "알림 이벤트 발생 시간")
    private Instant occurredAt;

    /**
     * ConvertedNotification (notification) --> UserNotificationResponse 전환 기능
     */
    public static UserNotificationResponse of(ConvertedNotification notification) {
        switch (notification.getType()) {
            case COMMENT -> {
                return CommentUserNotificationResponse.of((ConvertedCommentNotification) notification);
            }
            case LIKE -> {
                return LikeUserNotificationResponse.of((ConvertedLikeNotification) notification);
            }
            case FOLLOW -> {
                return FollowUserNotificationResponse.of((ConvertedFollowNotification) notification);
            }
            default -> throw new IllegalArgumentException("Unsupported notification type: " + notification.getType());
        }
    }
}
