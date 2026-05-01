package kr.co.daeun.notification.dto;

import jakarta.validation.constraints.NotBlank;
import kr.co.daeun.notification.type.ChannelType;
import lombok.Data;

@Data
public class CreateNotificationReqDTO {

    @NotBlank(message = "eventType required.")
    private String eventType;
    @NotBlank(message = "channelType required.")
    private ChannelType channelType;
    @NotBlank(message = "recipientKey required.")
    private String recipientKey;
    private String title;
    @NotBlank(message = "body required.")
    private String body;
    private String idempotencyKey;
    private String recipientValue;
}
