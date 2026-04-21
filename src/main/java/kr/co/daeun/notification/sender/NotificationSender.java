package kr.co.daeun.notification.sender;

import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationSendResult;

public interface NotificationSender {
    ChannelType getChannelType();
    NotificationSendResult send(NotificationDTO notification);
}