package kr.co.daeun.notification.dto;

import lombok.Data;

import java.time.LocalDateTime;

// 알림 단건 조회
@Data
public class NotificationDetailRespDTO {
    private Long notificationId;
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
    private LocalDateTime createdAt;
    private LocalDateTime processingStartedAt;
    private LocalDateTime sentAt;
}
