package kr.co.daeun.notification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationListItemDTO {
    private String notificationId;
    private String eventType;
    private String channelType;
    private String recipientKey;
    private String status;
    private int retryCnt;
    private String lastErrorCode;
    private LocalDateTime createdAt;
}
