package com.notification.repository;

import com.notification.domain.Notification;
import com.notification.domain.NotificationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    Optional<Notification> findById(String id);

    Notification save(Notification notification);

    void deleteById(String id);



    @Query("{ 'type': ?0, 'commentId': ?1 }")
    Optional<Notification> findByTypeAndCommentId(NotificationType type, long commentId);


    /**
     * 많은 호출 예상
     * 좋아요... 생성...
     */
    @Query("{ 'type': ?0, 'postId': ?1 }")
    Optional<Notification> findByTypeAndPostId(NotificationType type, long postId);


    @Query("{ 'type': ?0, 'userId': ?1, 'followerId': ?2 }")
    Optional<Notification> findByTypeAndUserIdAndFollowerId(NotificationType type, long userId, long followerId);



    /**
     * 한 유저의 알림 목록을 조회한다
     * Slice 를 사용하여 페이지네이션 처리
     *
     */
    Slice<Notification> findAllByUserIdOrderByOccurredAtDesc(long userId, Pageable page);

    /**
     * occurredAt : 알림 대상인 실제 이벤트가 발생한 시간 (댓슬, 좋아요, 팔로워)
     * OccurredAtLessThan : occurredAt 필드 값이 주어진 값보다 작은(이전인) 데이터를 찾는 조건입니다. 기준 시간이 작다는 건 기준 시간 보다 최신이다는 뜻
     * OrderByOccurredAtDesc : 결과를 occurredAt 필드 기준으로 내림차순(최신순)으로 정렬합니다.
     * 이 메서드의 전체 의미는 "특정 사용자(userId)의 데이터 중에서 특정 시간(occurredAt)보다 이전에 발생한 모든 데이터를 찾아서,
     * 발생 시간 기준으로 최신순(내림차순)으로 정렬하여 반환"입니다.
     */
    Slice<Notification> findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(long userId, Instant occurredAt, Pageable pageable);

    /**
     * 가장 많은 호출 예상
     * 내가 가진 알림 중에 가장 최신으로 업데이트된 알림의 시간 값, 제일 최신 값을 가져온다 (가장 큰 값), 1개
     */
    Optional<Notification> findFirstByUserIdOrderByLastUpdatedAtDesc(long userId);
}