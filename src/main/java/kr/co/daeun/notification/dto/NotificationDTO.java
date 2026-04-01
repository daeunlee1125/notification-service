package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private Long notificationId;
    private String idempotencyKey;
    private String eventType;
    private ChannelType channelType;
    private String recipientKey;
    private String recipientValue;
    private String title;
    private String body;
    private NotificationStatus status;
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
