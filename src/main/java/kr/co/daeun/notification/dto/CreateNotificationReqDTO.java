package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.ChannelType;
import lombok.Data;

@Data
public class CreateNotificationReqDTO {
    private String eventType;
    private ChannelType channelType;
    private String recipientKey;
    private String title;
    private String body;
    private String idempotencyKey;
    private String recipientValue;
}
