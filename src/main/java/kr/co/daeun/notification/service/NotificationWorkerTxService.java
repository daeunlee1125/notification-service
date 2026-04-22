package kr.co.daeun.notification.service;

import kr.co.daeun.notification.dto.DeliveryAttemptDTO;
import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.mapper.NotificationMapper;
import kr.co.daeun.notification.type.AttemptStatus;
import kr.co.daeun.notification.type.NotificationSendResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationWorkerTxService {

    private final NotificationMapper notificationMapper;

    @Transactional
    public NotificationDTO claimOne() {
        NotificationDTO target = notificationMapper.findNextProcessTarget();
        if (target == null) return null;

        int updated = notificationMapper.updateStatusToInProgress(target.getNotificationId(), target.getStatus());
        if (updated == 0) return null;

        return target;
    }

    @Transactional
    public void handleSuccess(NotificationDTO target, int attemptNo, LocalDateTime startedAt, LocalDateTime finishedAt) {
        notificationMapper.updateNotificationSuccess(target.getNotificationId());

        notificationMapper.insertDeliveryAttempt(
                DeliveryAttemptDTO.builder()
                        .notificationId(target.getNotificationId())
                        .attemptNo(attemptNo)
                        .attemptStatus(AttemptStatus.SUCCESS)
                        .startedAt(startedAt)
                        .finishedAt(finishedAt)
                        .build()
        );
    }

    @Transactional
    public void handleFailure(NotificationDTO target, int attemptNo, LocalDateTime startedAt, LocalDateTime finishedAt, NotificationSendResult result) {

        if (attemptNo >= target.getMaxRetryCnt()) {
            notificationMapper.updateNotificationDeadLetter(
                    NotificationDTO.builder()
                            .notificationId(target.getNotificationId())
                            .retryCnt(attemptNo)
                            .lastErrorCode(result.getErrorCode())
                            .lastErrorMessage(result.getErrorMessage())
                            .build()
            );
        } else {
            LocalDateTime nextRetryAt = calculateNextRetryAt(attemptNo);

            notificationMapper.updateNotificationRetry(
                    NotificationDTO.builder()
                            .notificationId(target.getNotificationId())
                            .retryCnt(attemptNo)
                            .nextRetryAt(nextRetryAt)
                            .lastErrorCode(result.getErrorCode())
                            .lastErrorMessage(result.getErrorMessage())
                            .build()
            );
        }

        notificationMapper.insertDeliveryAttempt(
                DeliveryAttemptDTO.builder()
                        .notificationId(target.getNotificationId())
                        .attemptNo(attemptNo)
                        .attemptStatus(AttemptStatus.FAIL)
                        .startedAt(startedAt)
                        .finishedAt(finishedAt)
                        .errorCode(result.getErrorCode())
                        .errorMessage(result.getErrorMessage())
                        .build()
        );
    }

    private LocalDateTime calculateNextRetryAt(int retryCnt) {
        return switch (retryCnt) {
            case 1 -> LocalDateTime.now().plusMinutes(1);
            case 2 -> LocalDateTime.now().plusMinutes(5);
            case 3 -> LocalDateTime.now().plusMinutes(15);
            default -> LocalDateTime.now().plusMinutes(30);
        };
    }
}