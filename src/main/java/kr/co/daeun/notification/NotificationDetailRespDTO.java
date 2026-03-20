package kr.co.daeun.notification;

import lombok.Data;

import java.time.LocalDateTime;

// 알림 단건 조회
@Data
public class NotificationDetailRespDTO {
    private int notificationId;
    private String eventType;
    private String channelType;
    private String recipientKey;
    private String title;
    private String body;
    private String status;
    private int retryCnt;
    private int maxRetryCnt;
    private LocalDateTime nextRetryAt;
    private String lastErrorCode;
    private String lastErrorMessage;
    private LocalDateTime requestedAt;
    private int processingStartedAt;
    private LocalDateTime sentAt;
}
