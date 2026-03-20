package kr.co.daeun.notification;

import lombok.Data;

@Data
public class CreateNotificationReqDTO {
    private String eventType;
    private String channelType;
    private String recipientKey;
    private String title;
    private String body;
    private String idempotencyKey;
}
