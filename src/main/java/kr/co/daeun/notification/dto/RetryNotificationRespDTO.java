package kr.co.daeun.notification.dto;

import lombok.Data;

@Data
public class RetryNotificationRespDTO {
    private Long notificationId;
    private String status;
}
