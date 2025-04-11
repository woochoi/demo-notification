package com.notification.service.dto;

import com.notification.domain.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;


/**
 * ConvertedCommentNotification extends ConvertedNotification
 * ConvertedLikeNotification extends ConvertedNotification
 * ConvertedFollowNotification extends ConvertedNotification
 */
@Getter
@AllArgsConstructor
public abstract class ConvertedNotification { // 필요한 값만, 실제 유저 화면에서 필요한 값들
    protected String id;
    protected NotificationType type;
    protected Instant occurredAt; // 알림 대상인 실제 이벤트가 발생한 시간
    protected Instant lastUpdatedAt; // 내가 가진 알림 중에 가장 최신으로 업데이트된 알림의 시간 값, 제일 최신 값을 가져온다
}
