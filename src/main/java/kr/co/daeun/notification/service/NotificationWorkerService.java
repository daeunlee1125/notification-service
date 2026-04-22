package kr.co.daeun.notification.service;

import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.mapper.NotificationMapper;
import kr.co.daeun.notification.sender.NotificationSender;
import kr.co.daeun.notification.sender.NotificationSenderResolver;
import kr.co.daeun.notification.type.NotificationSendResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationWorkerService {

    private final NotificationMapper notificationMapper;
    private final NotificationSenderResolver senderResolver;

    private final NotificationWorkerTxService txService;

    public void processOne() {
        NotificationDTO target = txService.claimOne();

        if (target == null) {
            log.info("처리할 알림 없음");
            return;
        }

        LocalDateTime startedAt = LocalDateTime.now();
        int nextAttemptNo = target.getRetryCnt() + 1;

        NotificationSendResult result;
        try {
            NotificationSender sender = senderResolver.getSender(target.getChannelType());
            result = sender.send(target);
        } catch (Exception e) {
            result = NotificationSendResult.fail("SEND_EXCEPTION", e.getMessage());
        }

        LocalDateTime finishedAt = LocalDateTime.now();

        if (result.isSuccess()) {
            txService.handleSuccess(target, nextAttemptNo, startedAt, finishedAt);
            log.info("발송 성공 notificationId={}", target.getNotificationId());
            return;
        }

        txService.handleFailure(target, nextAttemptNo, startedAt, finishedAt, result);
        log.info("발송 실패 notificationId={}", target.getNotificationId());
    }

    @Scheduled(fixedDelay = 10000)
    public void processOneBySchedule() {
        processOne();
    }
}