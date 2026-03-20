package kr.co.daeun.notification;

import lombok.Data;

@Data
public class RetryNotificationReqDTO {
    private String adminId;
    private String reason;
}
