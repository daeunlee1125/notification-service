package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.NotificationStatus;
import lombok.Data;

@Data
public class NotificationListSearchDTO {
    private NotificationStatus status;
    private String channelType;
    private Integer page = 1;
    private Integer size = 20;

    public int getOffset() {
        return (page - 1) * size;
    }
}
