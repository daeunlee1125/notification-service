package kr.co.daeun.notification.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeliveryAttemptsRespDTO {
    private Long notificationId;
    private List<DeliveryAttemptDTO> attempts;
}
