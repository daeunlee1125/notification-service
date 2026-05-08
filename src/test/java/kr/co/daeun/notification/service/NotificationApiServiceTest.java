package kr.co.daeun.notification.service;

import kr.co.daeun.notification.dto.CreateNotificationReqDTO;
import kr.co.daeun.notification.dto.CreateNotificationRespDTO;
import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.mapper.NotificationMapper;
import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationApiServiceTest {

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationApiService notificationApiService;

    @Mock
    private NotificationWorkerTxService notificationWorkerTxService;

    private CreateNotificationReqDTO reqDTO;

    @BeforeEach
    void setUp() {
        reqDTO = new CreateNotificationReqDTO();
        reqDTO.setEventType("ORDER_COMPLETED");
        reqDTO.setChannelType(ChannelType.EMAIL);
        reqDTO.setRecipientKey("user-1");
        reqDTO.setRecipientValue("test@test.com");
        reqDTO.setBody("hello");
        reqDTO.setIdempotencyKey("idem-1");
    }

    @Test
    void createNotification_success() {
        doAnswer(invocation -> {
            NotificationDTO dto = invocation.getArgument(0);
            dto.setNotificationId(1L);
            return null;
        }).when(notificationMapper).insertNotification(any(NotificationDTO.class));

        CreateNotificationRespDTO resp = notificationApiService.createNotification(reqDTO);

        assertThat(resp.getNotificationId()).isEqualTo(1L);
        assertThat(resp.getStatus()).isEqualTo(NotificationStatus.PENDING);
        verify(notificationMapper).insertNotification(any(NotificationDTO.class));
    }

    @Test
    void createNotification_duplicateIdempotency_returnsExisting() {
        doThrow(new DuplicateKeyException("duplicate")).when(notificationMapper).insertNotification(any(NotificationDTO.class));

        NotificationDTO existing = NotificationDTO.builder()
                .notificationId(99L)
                .status(NotificationStatus.PENDING)
                .build();

        when(notificationMapper.findByIdempotencyKey("idem-1")).thenReturn(existing);

        CreateNotificationRespDTO resp = notificationApiService.createNotification(reqDTO);

        assertThat(resp.getNotificationId()).isEqualTo(99L);
        assertThat(resp.getStatus()).isEqualTo(NotificationStatus.PENDING);
    }
}