package com.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Comment {
    private long id;
    private long userId;
    private String content;
    private LocalDateTime createdAt;
}
