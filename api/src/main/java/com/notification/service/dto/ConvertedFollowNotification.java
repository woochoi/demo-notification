package com.notification.service.dto;

import com.notification.domain.NotificationType;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ConvertedFollowNotification extends ConvertedNotification {
    private final String userName;
    private final String userProfileImageUrl;
    private final boolean isFollowing; // 팔로우 여부 : 팔로우 버튼 활성화 유무 판단

    public ConvertedFollowNotification(String id, NotificationType type,
                                       Instant occurredAt, Instant lastUpdatedAt,
                                       String userName, String userProfileImageUrl, boolean isFollowing) {
        super(id, type, occurredAt, lastUpdatedAt);
        this.userName = userName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.isFollowing = isFollowing;
    }
}
