package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.NotificationStatus;
import lombok.Data;

@Data
public class RetryNotificationRespDTO {
    private Long notificationId;
    private NotificationStatus status;
}
