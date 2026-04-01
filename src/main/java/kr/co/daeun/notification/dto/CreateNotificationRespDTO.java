package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.NotificationStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateNotificationRespDTO {
    private Long notificationId;
    private NotificationStatus status;

}
