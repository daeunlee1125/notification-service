package kr.co.daeun.notification.dto;

import kr.co.daeun.notification.type.AdminActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminActionLogDTO {
    Long logId;
    Long notificationId;
    String adminId;
    AdminActionType actionType;
    String actionReason;
    LocalDateTime createdAt;
}
