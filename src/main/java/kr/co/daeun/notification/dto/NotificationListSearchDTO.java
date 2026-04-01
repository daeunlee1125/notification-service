package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.NotificationStatus;

public class NotificationListSearchDTO {
    private NotificationStatus status;
    private String channelType;
    private int page;
    private int size;
}
