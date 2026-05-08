package kr.co.daeun.notification.service;

import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.mapper.NotificationMapper;
import kr.co.daeun.notification.sender.NotificationSender;
import kr.co.daeun.notification.sender.NotificationSenderResolver;
import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationSendResult;
import kr.co.daeun.notification.type.NotificationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationWorkerServiceTest {

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private NotificationSenderResolver senderResolver;

    @Mock
    private NotificationSender sender;

    @Mock
    private NotificationWorkerTxService txService;

    @InjectMocks
    private NotificationWorkerService notificationWorkerService;

    @Test
    void processOne_success_callsHandleSuccess() {
        NotificationDTO target = NotificationDTO.builder()
                .notificationId(1L)
                .channelType(ChannelType.EMAIL)
                .status(NotificationStatus.PENDING)
                .retryCnt(0)
                .maxRetryCnt(3)
                .body("ok")
                .build();

        when(txService.claimOne()).thenReturn(target);
        when(senderResolver.getSender(ChannelType.EMAIL)).thenReturn(sender);
        when(sender.send(target)).thenReturn(NotificationSendResult.success());

        notificationWorkerService.processOne();

        verify(txService).handleSuccess(
                eq(target),
                eq(1),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        );

        verify(txService, never()).handleFailure(
                any(NotificationDTO.class),
                anyInt(),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(NotificationSendResult.class)
        );
    }

    @Test
    void processOne_fail_callsHandleFailure() {
        NotificationDTO target = NotificationDTO.builder()
                .notificationId(1L)
                .channelType(ChannelType.EMAIL)
                .status(NotificationStatus.PENDING)
                .retryCnt(0)
                .maxRetryCnt(3)
                .body("fail")
                .build();

        NotificationSendResult result =
                NotificationSendResult.fail("SEND_FAIL", "smtp fail");

        when(txService.claimOne()).thenReturn(target);
        when(senderResolver.getSender(ChannelType.EMAIL)).thenReturn(sender);
        when(sender.send(target)).thenReturn(result);

        notificationWorkerService.processOne();

        verify(txService).handleFailure(
                eq(target),
                eq(1),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                eq(result)
        );

        verify(txService, never()).handleSuccess(
                any(NotificationDTO.class),
                anyInt(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        );
    }

    @Test
    void processOne_noTarget_doNothing() {
        when(txService.claimOne()).thenReturn(null);

        notificationWorkerService.processOne();

        verify(senderResolver, never()).getSender(any());
        verify(sender, never()).send(any());
        verify(txService, never()).handleSuccess(any(), anyInt(), any(), any());
        verify(txService, never()).handleFailure(any(), anyInt(), any(), any(), any());
    }

    @Test
    void processOne_fail_thenSuccess_onNextAttempt() {
        NotificationDTO first = NotificationDTO.builder()
                .notificationId(1L)
                .channelType(ChannelType.EMAIL)
                .status(NotificationStatus.PENDING)
                .retryCnt(0)
                .maxRetryCnt(3)
                .body("first fail")
                .build();

        NotificationDTO second = NotificationDTO.builder()
                .notificationId(1L)
                .channelType(ChannelType.EMAIL)
                .status(NotificationStatus.RETRY_SCHEDULED)
                .retryCnt(1)
                .maxRetryCnt(3)
                .body("second success")
                .build();

        NotificationSendResult failResult =
                NotificationSendResult.fail("SEND_FAIL", "smtp fail");

        when(txService.claimOne()).thenReturn(first, second);
        when(senderResolver.getSender(ChannelType.EMAIL)).thenReturn(sender);
        when(sender.send(first)).thenReturn(failResult);
        when(sender.send(second)).thenReturn(NotificationSendResult.success());

        notificationWorkerService.processOne();
        notificationWorkerService.processOne();

        verify(txService).handleFailure(
                eq(first),
                eq(1),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                eq(failResult)
        );

        verify(txService).handleSuccess(
                eq(second),
                eq(2),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        );
    }

    @Test
    void processOne_senderThrows_callsHandleFailureWithSendException() {
        NotificationDTO target = NotificationDTO.builder()
                .notificationId(1L)
                .channelType(ChannelType.EMAIL)
                .status(NotificationStatus.PENDING)
                .retryCnt(0)
                .maxRetryCnt(3)
                .body("fail")
                .build();

        when(txService.claimOne()).thenReturn(target);
        when(senderResolver.getSender(ChannelType.EMAIL)).thenReturn(sender);
        when(sender.send(target)).thenThrow(new RuntimeException("smtp down"));

        notificationWorkerService.processOne();

        verify(txService).handleFailure(
                eq(target),
                eq(1),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                argThat(result ->
                        !result.isSuccess()
                                && "SEND_EXCEPTION".equals(result.getErrorCode())
                                && "smtp down".equals(result.getErrorMessage())
                )
        );

        verify(txService, never()).handleSuccess(any(), anyInt(), any(), any());
    }
}