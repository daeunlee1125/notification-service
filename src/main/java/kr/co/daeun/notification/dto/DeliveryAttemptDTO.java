package kr.co.daeun.notification.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryAttemptDTO {
    private int attemptNo;
    private String attemptStatus;
    private String errorCode;
    private String errorMessage;
    private String providerResponse;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}
