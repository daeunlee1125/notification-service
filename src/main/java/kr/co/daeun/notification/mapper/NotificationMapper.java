package kr.co.daeun.notification.mapper;

import kr.co.daeun.notification.dto.CreateNotificationReqDTO;
import kr.co.daeun.notification.dto.NotificationDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotificationMapper {
    void insertNotification(NotificationDTO notificationDTO);
    NotificationDTO findByIdempotencyKey(String idempotencyKey);
}
