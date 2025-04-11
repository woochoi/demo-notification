package com.notification.service;

import com.notification.domain.Notification;
import com.notification.repository.NotificationRepository;
import com.notification.service.dto.GetUserNotificationsByPivotResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationListService {

    private final NotificationRepository repository;

    private static final int PAGE_SIZE = 20; // Page 사이즈 (몇개)

    public NotificationListService(NotificationRepository repository) {
        this.repository = repository;
    }


    /**
     * 한 유저의 알림 목록을 조회한다
     * Pivot(기준값: occurredAt, 알림 대상인 실제 이벤트가 발생한 시간) vs Paging
     * @param userId
     * @param occurredAt
     * @return
     */
    public GetUserNotificationsByPivotResult getUserNotificationsByPivot(long userId, Instant occurredAt) {

        // Page 보다는 단순함
        // 사용자가 스크롤을 내릴 때마다 가장 마지막으로 본 알림의 시간을 기준으로 그보다 이전의 알림들을 로드하는 방식으로 활용할 수 있습니다.
        Slice<Notification> result; // boolean hasNext(), isLast()

        if (occurredAt == null) { // 처음 알림 페이지에 들어간 경우 그냥 최신 20개 가져옴 (기준점이 없다)
            result = repository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0, PAGE_SIZE));
        } else { // occurredAt 있는 경우, occurredAt 조건 추가, occurredAt 의 피봇값 보다 작은 것 중에... 기준 시간이 작다는 건 기준 시간 보다 최신이다는 뜻
            result = repository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }

        /*
            public class GetUserNotificationsByPivotResult {
                private List<Notification> notifications;
                private boolean hasNext;
            }
         */
        return new GetUserNotificationsByPivotResult( // hasNext 값도 같이 내려줘야 하기 때문
                result.toList(),
                result.hasNext()
        );
    }

}
