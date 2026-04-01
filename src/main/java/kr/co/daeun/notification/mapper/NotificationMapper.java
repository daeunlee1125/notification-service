package kr.co.daeun.notification.mapper;

import kr.co.daeun.notification.dto.CreateNotificationReqDTO;
import kr.co.daeun.notification.dto.NotificationDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {
    void insertNotification(NotificationDTO notificationDTO);
    NotificationDTO findByIdempotencyKey(String idempotencyKey);
}
