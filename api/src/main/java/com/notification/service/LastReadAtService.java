package com.notification.service;


import com.notification.repository.NotificationReadRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component

public class LastReadAtService {

    private final NotificationReadRepository repository;

    public LastReadAtService(NotificationReadRepository repository) {
        this.repository = repository;
    }


    public LocalDateTime setLastReadAt(long userId) {
        //return repository.setLastReadAt(userId);
        return LocalDateTime.now();
    }

    public LocalDateTime getLastReadAt(long userId) {
        //return repository.getLastReadAt(userId);
        return LocalDateTime.now();
    }
}
