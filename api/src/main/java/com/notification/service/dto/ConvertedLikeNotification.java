package com.notification.service.dto;

import com.notification.domain.NotificationType;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ConvertedLikeNotification extends ConvertedNotification { // 좋아요 알림 화면에 필요한 정보
    private final String userName;
    private final String userProfileImageUrl;
    private final long userCount; // 좋아요가 N개 일때 (N-1)명
    private final String postImageUrl;

    public ConvertedLikeNotification(String id, NotificationType type,
                                     Instant occurredAt, Instant lastUpdatedAt,
                                     String userName, String userProfileImageUrl,
                                     long userCount, String postImageUrl) {
        super(id, type, occurredAt, lastUpdatedAt);
        this.userName = userName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.userCount = userCount;
        this.postImageUrl = postImageUrl;
    }
}
