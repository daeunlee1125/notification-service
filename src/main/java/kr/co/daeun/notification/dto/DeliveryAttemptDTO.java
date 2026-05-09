package kr.co.daeun.notification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishedAt;
}
