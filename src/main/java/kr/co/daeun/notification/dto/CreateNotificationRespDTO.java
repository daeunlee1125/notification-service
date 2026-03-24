package kr.co.daeun.notification.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateNotificationRespDTO {
    private Long notificationId;
    private String status;

}
