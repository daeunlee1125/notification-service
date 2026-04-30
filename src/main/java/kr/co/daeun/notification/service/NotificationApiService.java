package kr.co.daeun.notification.service;

import kr.co.daeun.notification.dto.*;
import kr.co.daeun.notification.exception.BadRequestException;
import kr.co.daeun.notification.exception.ConflictException;
import kr.co.daeun.notification.exception.NotFoundException;
import kr.co.daeun.notification.mapper.NotificationMapper;
import kr.co.daeun.notification.type.AdminActionType;
import kr.co.daeun.notification.type.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
            throw new BadRequestException("Request body required.");
        }
        if (reqDTO.getEventType() == null || reqDTO.getEventType().isBlank()) {
            throw new BadRequestException("eventType required.");
        }
        if (reqDTO.getChannelType() == null) {
            throw new BadRequestException("channelType required.");
        }
        if (reqDTO.getRecipientKey() == null || reqDTO.getRecipientKey().isBlank()) {
            throw new BadRequestException("recipientKey required.");
        }
        if (reqDTO.getBody() == null || reqDTO.getBody().isBlank()) {
            throw new BadRequestException("body required.");
        }
    }

    public NotificationDTO getNotificationDetail(Long notificationId){
        return notificationMapper.findByNotificationId(notificationId);
    }

    public NotificationListRespDTO getNotificationList(NotificationListSearchDTO searchDTO) {
        if (searchDTO == null) {
            searchDTO = new NotificationListSearchDTO();
        }

        if (searchDTO.getPage() == null || searchDTO.getPage() < 1) {
            throw new BadRequestException("page must be >= 1");
        }
        if (searchDTO.getSize() == null || searchDTO.getSize() < 1) {
            throw new BadRequestException("size must be >= 1");
        }

        List<NotificationListItemDTO> items = notificationMapper.getNotificationList(searchDTO);
        int totalCnt = notificationMapper.getNotificationListTotalCnt(searchDTO);

        NotificationListRespDTO respDTO = new NotificationListRespDTO();
        respDTO.setItems(items);
        respDTO.setPage(searchDTO.getPage());
        respDTO.setSize(searchDTO.getSize());
        respDTO.setTotalCnt(totalCnt);

        return respDTO;
    }

    public DeliveryAttemptsRespDTO getDeliveryAttempts(long notificationId){
        List<DeliveryAttemptDTO> attempts = notificationMapper.findDeliveryAttemptsByNotId(notificationId);

        DeliveryAttemptsRespDTO respDTO = new DeliveryAttemptsRespDTO();
        respDTO.setAttempts(attempts);
        respDTO.setNotificationId(notificationId);

        return respDTO;
    }

    @Transactional
    public RetryNotificationRespDTO retryNotification(Long notificationId, RetryNotificationReqDTO reqDTO) {

        NotificationDTO notification = notificationMapper.findByNotificationId(notificationId);

        if (notification == null) {
            throw new NotFoundException("notification not found. id=" + notificationId);
        }

        if (notification.getStatus() != NotificationStatus.DEAD_LETTER && notification.getStatus() != NotificationStatus.RETRY_SCHEDULED) {
            throw new ConflictException("retry not allowed for current status: " + notification.getStatus());
        }

        int updated = notificationMapper.updateStatusForManualRetry(notificationId, String.valueOf(notification.getStatus()));

        if (updated == 0) {
            throw new ConflictException("notification status changed before retry request.");
        }

        notificationMapper.insertAdminActionLog(
                AdminActionLogDTO.builder()
                        .notificationId(notificationId)
                        .adminId(reqDTO != null ? reqDTO.getAdminId() : null)
                        .actionType(String.valueOf(AdminActionType.RETRY))
                        .actionReason(reqDTO != null ? reqDTO.getReason() : null)
                        .build()
        );

        return RetryNotificationRespDTO.builder()
                .notificationId(notificationId)
                .status(NotificationStatus.PENDING)
                .build();
    }

    public NotificationStatsRespDTO getNotificationStats(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new BadRequestException("from, to required.");
        }
        if (!from.isBefore(to)) {
            throw new BadRequestException("from must be earlier than to.");
        }

        NotificationStatsRespDTO summary = notificationMapper.getNotificationStatsSummary(from, to);
        List<ChannelStatsRespDTO> channelStats = notificationMapper.getChannelStats(from, to);

        return NotificationStatsRespDTO.builder()
                .from(from)
                .to(to)
                .totalCnt(summary.getTotalCnt())
                .pendingCnt(summary.getPendingCnt())
                .inProgressCnt(summary.getInProgressCnt())
                .sentCnt(summary.getSentCnt())
                .retryScheduledCnt(summary.getRetryScheduledCnt())
                .deadLetterCnt(summary.getDeadLetterCnt())
                .channelStats(channelStats)
                .build();
    }

}
