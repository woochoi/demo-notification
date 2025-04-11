package com.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@Document("notifications")
public abstract class Notification {
    @Field(targetType = FieldType.STRING)   // ObjectId('123') -> "123"
    private String id; // *실제 사용자 화면에서는 필요 없는 정보

    private long userId; // 누구의 알림인가?, 좋아요,팔로우를 받은 사람
    private NotificationType type;
    private Instant occurredAt; // 알림 대상인 실제 이벤트가 발생한 시간
    private Instant createdAt; // *실제 사용자 화면에서는 필요 없는 정보
    private Instant lastUpdatedAt; // 내가 가진 알림 중에 가장 최신으로 업데이트된 알림의 시간 값, 제일 최신 값을 가져온다
    private Instant deletedAt;  // 알림이 삭제될 시간, *실제 사용자 화면에서는 필요 없는 정보
}
