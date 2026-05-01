package kr.co.daeun.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminActionListRespDTO {
    private Long notificationId;
    private List<AdminActionLogDTO> actions;
}