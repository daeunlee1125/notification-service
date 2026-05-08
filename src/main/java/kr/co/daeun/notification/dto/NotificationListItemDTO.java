package kr.co.daeun.notification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationListItemDTO {
    private Long notificationId;
    private String eventType;
    private ChannelType channelType;
    private String recipientKey;
    private NotificationStatus status;
    private int retryCnt;
    private String lastErrorCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String lastErrorMessage;
}
