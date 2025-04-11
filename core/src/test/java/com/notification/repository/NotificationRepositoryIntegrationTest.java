package com.notification.repository;

import com.notification.IntegrationTest;
import com.notification.domain.CommentNotification;
import com.notification.domain.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.*;
import java.util.Optional;

import static com.notification.domain.NotificationType.COMMENT;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private NotificationRepository sut;

    private final long userId = 2L;
    private final long postId = 3L;
    private final long writerId = 4L;
    private final long commentId = 5L;
    private final String comment = "comment";
    private final Instant now = Instant.now();
    private final Instant ninetyDaysAfter = now.plus(Duration.ofDays(90));


    /*
    long epochSeconds1 = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

    // 특정 시간대를 명시적으로 지정하고 싶다면
    long epochSeconds2 = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().getEpochSecond();

    // toEpochSecond(ZoneOffset) 메서드 사용
    long epochSecond3 = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

    // 특정 시간대 오프셋 지정
    long epochSecond4 =LocalDateTime.now().toEpochSecond(ZoneOffset.of("+09:00"));
    // 시스템 기본 시간대 사용:
    long epochSecond5 = LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .getEpochSecond();
    */

    // 1일부터 5일 전까지의 시간으로 발생 시간을 설정하고, 현재 시간 및 현재 시간에서 90일 후의 만료 시간으로 알림을 생성하여 저장합니다.
    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 5; i++) {
            Instant occurredAt = now.minus(Duration.ofDays(i)); // Instant occurredAt = now.minusDays(i);
            sut.save(new CommentNotification("id-" + i, userId, COMMENT, occurredAt, now, now, ninetyDaysAfter,
                    postId, writerId, comment, commentId));
        }
    }

    //@AfterEach
    void tearDown() {
        sut.deleteAll();
    }

   // @Test
    void testSave() {
        String id = "1";
        sut.save(createCommentNotification(id));
        Optional<Notification> optionalNotification = sut.findById(id);

        assertTrue(optionalNotification.isPresent());
    }

    //@Test
    void testFindById() {
        String id = "2";

        sut.save(createCommentNotification(id));
        Optional<Notification> optionalNotification = sut.findById(id);

        CommentNotification notification = (CommentNotification) optionalNotification.orElseThrow();
        assertEquals(notification.getId(), id);
        assertEquals(notification.getUserId(), userId);
        assertEquals(notification.getOccurredAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getCreatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getLastUpdatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getDeletedAt().getEpochSecond(), ninetyDaysAfter.getEpochSecond());
        assertEquals(notification.getPostId(), postId);
        assertEquals(notification.getWriterId(), writerId);
        assertEquals(notification.getComment(), comment);
        assertEquals(notification.getCommentId(), commentId);
    }

    //@Test
    void testDeleteById() {
        String id = "3";

        sut.save(createCommentNotification(id));
        sut.deleteById(id);
        Optional<Notification> optionalNotification = sut.findById(id);

        assertFalse(optionalNotification.isPresent());
    }


    /**
     *  Slice<Notification>
     *      findAllByUserIdOrderByOccurredAtDesc
     *  (long userId, Pageable page);
     */
    @Test
    void testFindAllByUserIdOrderByOccurredAtDesc() {
        Pageable pageable = PageRequest.of(0, 3); // 사전에 총 5개 준비
        Slice<Notification> result =  sut.findAllByUserIdOrderByOccurredAtDesc(userId, pageable);

        assertEquals(3, result.getContent().size());
        assertTrue(result.hasNext());

        Notification first = result.getContent().get(0);
        Notification second = result.getContent().get(1);
        Notification third = result.getContent().get(2);

        //
        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
        assertTrue(second.getOccurredAt().isAfter(third.getOccurredAt()));
    }

    /**
     * 1. 처음 쿼리 했을때 - Pivot 값은 :현재값
     * Slice<Notification>
     *     findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc
     * (long userId, Instant occurredAt, Pageable pageable);
     */
    //@Test
    void testFindAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDescFirstQuery() {
        Pageable pageable = PageRequest.of(0, 3);

        // 기준값인 현재시간 보다 다 작을것... 저장된 알림들은.. 최신부터 3개를 가져온다.
        Slice<Notification> result
                = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, now, pageable);

        assertEquals(3, result.getContent().size());
        assertTrue(result.hasNext());

        Notification first = result.getContent().get(0);
        Notification second = result.getContent().get(1);
        Notification third = result.getContent().get(2);

        // 정렬 검증
        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
        assertTrue(second.getOccurredAt().isAfter(third.getOccurredAt()));
    }


    /**
     * 2. 번째 쿼리 했을때 - Pivot 값은 : 첫번째에서 받은 알림들의 제일 마지막 알림의 OccurredAt 을 넘겨준다
     */
    //@Test
    void testFindAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDescSecondQueryWithPivot() {
        Pageable pageable = PageRequest.of(0, 3);

        Slice<Notification> firstResult
                = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, now, pageable);

        //assertTrue(firstResult.hasNext());
        
        Notification last = firstResult.getContent().get(2); // 0,1,2 중에 가장 마지막(2)이 기준이 된다
        Instant pivot = last.getOccurredAt(); // 기준값 다시 설정

        // pivot 가지고 다시 요청
        Slice<Notification> secondResult
                = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, pivot, pageable);

        assertEquals(2, secondResult.getContent().size());
        assertFalse(secondResult.hasNext());

        // 총 5개 중 첫번째 3개, 두번째 2개 이기 때문에
        Notification first = secondResult.getContent().get(0);
        Notification second = secondResult.getContent().get(1);

        // 정렬 검증
        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
    }

    private CommentNotification createCommentNotification(String id) {
        return new CommentNotification(id,
                userId,
                COMMENT,
                now,
                now,
                now,
                ninetyDaysAfter,
                postId,
                writerId,
                comment,
                commentId);
    }
}

