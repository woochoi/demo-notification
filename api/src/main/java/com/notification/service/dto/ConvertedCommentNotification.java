package com.notification.service.dto;

import com.notification.domain.NotificationType;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ConvertedCommentNotification extends ConvertedNotification { // 댓글 알림 화면에 필요한 정보
    private final String userName; // 유저명 > 유저 프로필이미지...님이 댓글을 남겼습니다. 댓글 내용... 댓글단 게시물의 이미지
    private final String userProfileImageUrl;
    private final String comment; // 댓글 원본 (수정불가)
    private final String postImageUrl; // 게시물 이미지

    public ConvertedCommentNotification(String id, NotificationType type,
                                        Instant occurredAt, Instant lastUpdatedAt,
                                        String userName, String userProfileImageUrl, String comment,
                                        String postImageUrl) {
        super(id, type, occurredAt, lastUpdatedAt);
        this.userName = userName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.comment = comment;
        this.postImageUrl = postImageUrl;
    }
}
