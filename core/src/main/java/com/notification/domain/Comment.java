package com.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Comment {
    private long id;
    private long userId;    // 댓글 작성자
    private String content; // 댓글 내용
    private LocalDateTime createdAt;
}
