package kr.co.daeun.notification.controller;

import kr.co.daeun.notification.service.NotificationWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/worker")
public class NotificationWorkerController {

    private final NotificationWorkerService notificationWorkerService;

    @PostMapping("/run-once")
    public ResponseEntity<String> runOnce() {
        notificationWorkerService.processOne();
        return ResponseEntity.ok("worker executed");
    }
}