package kr.co.daeun.notification.mapper;

import kr.co.daeun.notification.dto.AdminActionLogDTO;
import kr.co.daeun.notification.dto.DeliveryAttemptDTO;
import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.type.NotificationStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void insertNotification(NotificationDTO notificationDTO);

    NotificationDTO findByIdempotencyKey(String idempotencyKey);
    NotificationDTO findByNotificationId(Long notificationId);

    NotificationDTO findNextProcessTarget();

    int updateStatusToInProgress(Long notificationId, NotificationStatus currentStatus);

    int updateNotificationSuccess(Long notificationId);

    int updateNotificationRetry(NotificationDTO notificationDTO);

    int updateNotificationDeadLetter(NotificationDTO notificationDTO);

    void insertDeliveryAttempt(DeliveryAttemptDTO attemptDTO);

    List<DeliveryAttemptDTO> findDeliveryAttemptsByNotId(Long notificationId);

    int updateStatusForManualRetry(Long notificationId, String currentStatus);

    void insertAdminActionLog(AdminActionLogDTO adminActionLogDTO);
}
