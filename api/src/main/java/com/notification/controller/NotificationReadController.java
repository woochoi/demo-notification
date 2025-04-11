package com.notification.controller;

import com.notification.response.SetLastReadAtResponse;
import com.notification.service.LastReadAtService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

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
        Instant lastReadAt = service.setLastReadAt(userId);
        return new SetLastReadAtResponse(lastReadAt);
    }

    @PostMapping("/{userId}/update-last-read")
    public SetLastReadAtResponse setLastReadAt2(
            @PathVariable(value = "userId") long userId
    ) {
        Instant lastReadAt = service.setLastReadAt(userId);
        return new SetLastReadAtResponse(lastReadAt);
    }
}
