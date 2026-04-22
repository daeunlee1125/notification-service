package kr.co.daeun.notification.mapper;

import kr.co.daeun.notification.dto.CreateNotificationReqDTO;
import kr.co.daeun.notification.dto.DeliveryAttemptDTO;
import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.dto.NotificationDetailRespDTO;
import kr.co.daeun.notification.type.NotificationStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void insertNotification(NotificationDTO notificationDTO);

    NotificationDTO findByIdempotencyKey(String idempotencyKey);
    NotificationDetailRespDTO findByNotificationId(Long notificationId);

    NotificationDTO findNextProcessTarget();

    int updateStatusToInProgress(Long notificationId, NotificationStatus currentStatus);

    int updateNotificationSuccess(Long notificationId);

    int updateNotificationRetry(NotificationDTO notificationDTO);

    int updateNotificationDeadLetter(NotificationDTO notificationDTO);

    void insertDeliveryAttempt(DeliveryAttemptDTO attemptDTO);

    List<DeliveryAttemptDTO> findDeliveryAttemptsByNotId(Long notificationId);

}
