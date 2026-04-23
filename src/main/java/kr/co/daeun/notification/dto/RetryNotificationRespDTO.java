package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetryNotificationRespDTO {
    private Long notificationId;
    private NotificationStatus status;
}
