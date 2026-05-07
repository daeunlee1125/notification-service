package kr.co.daeun.notification.controller;

import jakarta.validation.Valid;
import kr.co.daeun.notification.dto.*;
import kr.co.daeun.notification.service.NotificationApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationApiController {

    private final NotificationApiService notificationApiService;

    @PostMapping
    public ResponseEntity<CreateNotificationRespDTO> createNotification(@Valid @RequestBody CreateNotificationReqDTO reqDTO) {
        return ResponseEntity.ok().body(notificationApiService.createNotification(reqDTO));
    }

    @GetMapping
    public ResponseEntity<NotificationListRespDTO> getNotificationList(NotificationListSearchDTO searchDTO) {
        return ResponseEntity.ok().body(notificationApiService.getNotificationList(searchDTO));
    }

    @GetMapping("/stats")
    public ResponseEntity<NotificationStatsRespDTO>  getNotificationStats(@RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationApiService.getNotificationStats(from, to));
    }

    @GetMapping("/{notificationId}/attempts")
    public ResponseEntity<DeliveryAttemptsRespDTO> getDeliveryAttempts(@PathVariable("notificationId") Long notificationId) {
        return ResponseEntity.ok().body(notificationApiService.getDeliveryAttempts(notificationId));
    }

    @PostMapping("/{notificationId}/retry")
    public ResponseEntity<RetryNotificationRespDTO> retryNotification(@PathVariable("notificationId") Long notificationId, @Valid @RequestBody RetryNotificationReqDTO reqDTO) {
        return ResponseEntity.ok().body(notificationApiService.retryNotification(notificationId, reqDTO));
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO> getNotificationDetail(@PathVariable("notificationId") Long notificationId) {
        return ResponseEntity.ok().body(notificationApiService.getNotificationDetail(notificationId));
    }

    @GetMapping("/{notificationId}/admin-actions")
    public ResponseEntity<AdminActionListRespDTO> getAdminActions(
            @PathVariable("notificationId") Long notificationId
    ) {
        return ResponseEntity.ok(notificationApiService.getAdminActions(notificationId));
    }

}
