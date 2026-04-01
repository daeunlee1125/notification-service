package kr.co.daeun.notification.service;

import kr.co.daeun.notification.dto.*;
import kr.co.daeun.notification.mapper.NotificationMapper;
import kr.co.daeun.notification.type.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationApiService {
    private final NotificationMapper notificationMapper;

    @Transactional
    public CreateNotificationRespDTO createNotification(CreateNotificationReqDTO reqDTO) {
        validateCreateNotificationRequest(reqDTO);

        try {
            NotificationDTO notification = NotificationDTO.builder()
                    .eventType(reqDTO.getEventType())
                    .channelType(reqDTO.getChannelType())
                    .recipientKey(reqDTO.getRecipientKey())
                    .recipientValue(reqDTO.getRecipientValue())
                    .title(reqDTO.getTitle())
                    .body(reqDTO.getBody())
                    .idempotencyKey(reqDTO.getIdempotencyKey())
                    .status(NotificationStatus.PENDING)
                    .retryCnt(0)
                    .build();

            notificationMapper.insertNotification(notification);

            return CreateNotificationRespDTO.builder()
                    .notificationId(notification.getNotificationId())
                    .status(notification.getStatus())
                    .build();

        } catch (DuplicateKeyException e) {
            NotificationDTO existing = notificationMapper.findByIdempotencyKey(reqDTO.getIdempotencyKey());

            return CreateNotificationRespDTO.builder()
                    .notificationId(existing.getNotificationId())
                    .status(existing.getStatus())
                    .build();
        }
    }

    private void validateCreateNotificationRequest(CreateNotificationReqDTO reqDTO) {
        if (reqDTO == null) {
            throw new IllegalArgumentException("Request body required.");
        }
        if (reqDTO.getEventType() == null || reqDTO.getEventType().isBlank()) {
            throw new IllegalArgumentException("eventType required.");
        }
        if (reqDTO.getChannelType() == null) {
            throw new IllegalArgumentException("channelType required.");
        }
        if (reqDTO.getRecipientKey() == null || reqDTO.getRecipientKey().isBlank()) {
            throw new IllegalArgumentException("recipientKey required.");
        }
        if (reqDTO.getBody() == null || reqDTO.getBody().isBlank()) {
            throw new IllegalArgumentException("body required.");
        }
    }

    public NotificationDetailRespDTO getNotificationDetail(long notificationId){
        return null;
    }

    public NotificationListRespDTO getNotificationList(NotificationListSearchDTO searchDTO
    ) {
        return null;
    }

    public DeliveryAttemptsRespDTO getDeliveryAttempts(long notificationId){
        return null;
    }

    public RetryNotificationRespDTO retryNotification(long notificationId, RetryNotificationReqDTO reqDTO){
        return null;
    }

    public NotificationStatsRespDTO getNotificationStats(LocalDateTime from, LocalDateTime to){
        return null;
    }
}
