package kr.co.daeun.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RetryNotificationReqDTO {
    @NotBlank(message = "adminId required.")
    private String adminId;
    private String reason;
}
