package kr.co.daeun.notification.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private Long notificationId;
    private String idempotencyKey;
    private String eventType;
    private String channelType;
    private String recipientKey;
    private String recipientValue;
    private String title;
    private String body;
    private String status;
    private int retryCnt;
    private int maxRetryCnt;
    private LocalDateTime nextRetryAt;
    private String lastErrorCode;
    private String lastErrorMessage;
    private LocalDateTime processingStartedAt;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
