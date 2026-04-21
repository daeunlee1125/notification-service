package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.AttemptStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class DeliveryAttemptDTO {
    private Long attemptId;
    private int attemptNo;
    private Long notificationId;
    private AttemptStatus attemptStatus;
    private String errorCode;
    private String errorMessage;
    private String providerResponse;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}
