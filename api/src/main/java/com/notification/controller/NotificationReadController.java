package com.notification.controller;

import com.notification.response.SetLastReadAtResponse;
import com.notification.service.LastReadAtService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/user-notifications")
public class NotificationReadController implements NotificationReadControllerSpec {

    private final LastReadAtService service;

    public NotificationReadController(LastReadAtService service) {
        this.service = service;
    }

    @Override
    @PutMapping("/{userId}/read")
    public SetLastReadAtResponse setLastReadAt(
            @PathVariable(value = "userId") long userId
    ) {
        LocalDateTime lastReadAt = service.setLastReadAt(userId);
        return new SetLastReadAtResponse(lastReadAt);
    }
}
