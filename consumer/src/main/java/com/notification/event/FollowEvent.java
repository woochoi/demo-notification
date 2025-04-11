package com.notification.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowEvent {
    private FollowEventType type;
    private long userId; // 팔로워를 신청한 사람
    private long targetUserId; // 에게 알림을 생성해줘야 (신청 당한 사람)
    private LocalDateTime createdAt;
}
